package sovcombank.jabka.studyservice.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.sovcombank.openapi.model.HomeworkOpenAPI;
import ru.sovcombank.openapi.user.ApiException;
import ru.sovcombank.openapi.user.api.UserApi;
import sovcombank.jabka.studyservice.exceptions.BadRequestException;
import sovcombank.jabka.studyservice.exceptions.NotFoundException;
import sovcombank.jabka.studyservice.mappers.HomeworkMapper;
import sovcombank.jabka.studyservice.models.FileName;
import sovcombank.jabka.studyservice.models.Homework;
import sovcombank.jabka.studyservice.models.StudyMaterials;
import sovcombank.jabka.studyservice.repositories.HomeworkRepository;
import sovcombank.jabka.studyservice.repositories.StudyMaterialsRepository;
import sovcombank.jabka.studyservice.services.interfaces.FileNameService;
import sovcombank.jabka.studyservice.services.interfaces.FileService;
import sovcombank.jabka.studyservice.services.interfaces.HomeworkService;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HomeworkServiceImpl implements HomeworkService {
    private final HomeworkRepository homeworkRepository;
    private final StudyMaterialsRepository studyMaterialsRepository;

    private final HomeworkMapper homeworkMapper;
    private final FileService fileService;

    private final FileNameService fileNameService;

    private final UserApi userApi;

    @Override
    @Transactional
    public void createHomework(Long materialsId, HomeworkOpenAPI homeworkOpenApi, List<MultipartFile> files) {
        if(files.isEmpty()){
            throw new BadRequestException("Files is empty");
        }
        StudyMaterials studyMaterials =
                studyMaterialsRepository.findById(materialsId).orElseThrow(() ->
                        new BadRequestException(String.format("Materials with id %d not found", materialsId)));

        //TODO: проверить, что юзер существует через client api

        Homework homework = homeworkMapper.toHomework(homeworkOpenApi);

        setHomeworkFileNamesAndSaveFiles(files, homework);

        studyMaterials.getHomeworks().add(homework);
        studyMaterialsRepository.save(studyMaterials);
    }

    @Override
    @Transactional
    public void deleteHomework(Long materialsId, Long homeworkId){
        Homework homework = homeworkRepository.findById(homeworkId)
                .orElseThrow(()->new NotFoundException(String.format("Homework with id %d not found",homeworkId)));
        homework.getFileNames()
                .forEach((fileName)->{
                    fileService.removeFileByPath(getFilePath(fileName));
                });
        homeworkRepository.delete(homework);
    }

    @Override
    @Transactional
    public List<Homework> getAllHomeworkByMaterialsId(Long materialsId) {
        StudyMaterials studyMaterials = studyMaterialsRepository.findById(materialsId)
                .orElseThrow(()->new NotFoundException(String.format("Materials with id %d not found",materialsId)));
        return studyMaterials.getHomeworks().stream().toList();
    }

    @Override
    @Transactional
    public Homework getHomeworkById(Long id) {
        return homeworkRepository.findById(id)
                .orElseThrow(()->new NotFoundException(String.format("Homework with id %d not found",id)));
    }

    @Override
    @Transactional
    public Homework getHomeworkByStudentAndMaterials(Long materialsId, Long studentId) {
        try {
            userApi.showUserInfoWithHttpInfo(studentId);
        } catch (ApiException e) {
            throw new RuntimeException(e);
        }

        StudyMaterials studyMaterials = studyMaterialsRepository.findById(materialsId)
                .orElseThrow(()->new NotFoundException(String.format("Materials with id %d not found",materialsId)));
        return studyMaterials.getHomeworks().stream().filter(homework -> homework.getStudentId().equals(studentId)).findAny()
                .orElseThrow(()->new NotFoundException(String.format("Homework by student with id {%d} in materials with id {%d} not found.",studentId,materialsId)));
    }

    @Override
    @Transactional
    public List<Homework> getHomeworksByStudentId(Long studentId) {

        Integer statusCode;
        try {
            statusCode = userApi.showUserInfoWithHttpInfo(studentId).getStatusCode();
        } catch (ApiException e) {
            throw new RuntimeException(e);
        }
        if(statusCode < 200 || statusCode > 300){
                throw new BadRequestException(String.format("Student with id %d not found", studentId));
            }
        return homeworkRepository.findByStudentId(studentId);
    }

    @Override
    @Transactional
    public void updateHomework(HomeworkOpenAPI homeworkOpenAPI, List<MultipartFile> files) {
        if(homeworkOpenAPI.getId() == null){
            throw new BadRequestException("Homework id is null");
        }
        Homework homework = this.getHomeworkById(homeworkOpenAPI.getId());

        homework.getFileNames().forEach((fileName)->{
            fileService.removeFileByPath(getFilePath(fileName));
        });
        setHomeworkFileNamesAndSaveFiles(files, homework);
        homeworkRepository.save(homework);
    }

    private String getFilePath(FileName fileName){
        return String.format("%s%s",FileService.HOMEWORK_PREFIX,fileName.getNameS3());
    }

    private void setHomeworkFileNamesAndSaveFiles(List<MultipartFile> files, Homework homework){
        if(files.isEmpty()){
            throw new BadRequestException("Files is empty");
        }
        Set<FileName> fileNames = files.stream()
                .map((file) -> {
                            FileName fileName = FileName.builder()
                                    .initialName(file.getName())
                                    .nameS3(fileService.generateHomeworkFileName(file.getName(), homework.getStudentId()))
                                    .build();
                            fileService.save(getFilePath(fileName), file);
                            return fileName;
                        }
                )
                .map(fileNameService::saveFileName)
                .collect(Collectors.toSet());
        homework.setFileNames(fileNames);
    }
}
