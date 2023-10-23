package sovcombank.jabka.studyservice.services;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.sovcombank.openapi.model.StudyMaterialsBody;
import ru.sovcombank.openapi.model.StudyMaterialsOpenAPI;
import sovcombank.jabka.studyservice.exceptions.BadRequestException;
import sovcombank.jabka.studyservice.exceptions.NotFoundException;
import sovcombank.jabka.studyservice.mappers.StudyMaterialsMapper;
import sovcombank.jabka.studyservice.models.FileName;
import sovcombank.jabka.studyservice.models.StudyMaterials;
import sovcombank.jabka.studyservice.models.Subject;
import sovcombank.jabka.studyservice.repositories.HomeworkRepository;
import sovcombank.jabka.studyservice.repositories.StudyMaterialsRepository;
import sovcombank.jabka.studyservice.repositories.SubjectRepository;
import sovcombank.jabka.studyservice.services.interfaces.FileNameService;
import sovcombank.jabka.studyservice.services.interfaces.FileService;
import sovcombank.jabka.studyservice.services.interfaces.StudyMaterialsService;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;


//todo: не забудь что тут с файлами еще дофига работы (сохранять, удалять из бд FileName и в S3)
//todo: мб при сохранении надоп проверять, что Subject, для которого добавляется материалс существует
@Service
@RequiredArgsConstructor
public class StudyMaterialsServiceImpl implements StudyMaterialsService {
    private final StudyMaterialsMapper materialsMapper;
    private final StudyMaterialsRepository materialsRepository;
    private final FileService fileService;
    private final FileNameService fileNameService;
    private final SubjectRepository subjectRepository;

    @Transactional
    @Override
    public ResponseEntity<Void> createMaterials(StudyMaterialsBody studyMaterialsBody) {
        StudyMaterialsOpenAPI studyMaterialsOpenAPI = studyMaterialsBody.getStudyMaterials();
        StudyMaterials studyMaterials = materialsMapper.toStudyMaterials(studyMaterialsOpenAPI);
        if (!subjectRepository.existsById(studyMaterials.getSubject().getId())) {
            return ResponseEntity
                    .notFound()
                    .build();
        }
        List<Resource> files = studyMaterialsBody.getFiles();
        setMaterialsFileNamesAndSaveFiles(files, studyMaterials);
        materialsRepository.save(studyMaterials);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .build();
    }

    @Transactional
    @Override
    public ResponseEntity<Void> deleteMaterials(Long id) {
        Optional<StudyMaterials> studyMaterialsOpt = materialsRepository.findById(id);
        if (studyMaterialsOpt.isEmpty()) {
            return ResponseEntity
                    .notFound()
                    .build();
        }
        StudyMaterials studyMaterials = studyMaterialsOpt.get();
        studyMaterials.getAttachedFiles()
                .forEach((fileName) -> fileService.removeFileByPath(getMaterialsFilePath(fileName)));
        materialsRepository.deleteById(id);
        return ResponseEntity
                .ok()
                .build();
    }

    @Transactional
    @Override
    public List<StudyMaterials> getAllMaterials() {
        List<StudyMaterials> studyMaterials = materialsRepository.findAll();
        if (studyMaterials.isEmpty()) {
            throw new NotFoundException("No Materials Were Not Found");
        }
        return studyMaterials;
    }

    @Transactional
    @Override
    public StudyMaterials getMaterialsById(Long id) {
        Optional<StudyMaterials> studyMaterialsOpt = materialsRepository.findById(id);
        if(studyMaterialsOpt.isEmpty()){
            throw new NotFoundException(String.format(
                            "No Materials With Id %d Were Not Found", id));
        }
        return studyMaterialsOpt.get();
    }

    @Transactional
    @Override
    public ResponseEntity<Void> updateMaterials(StudyMaterialsBody studyMaterialsBody) {
        StudyMaterialsOpenAPI studyMaterialsOpenAPI = studyMaterialsBody.getStudyMaterials();
        Optional<StudyMaterials> studyMaterialsOpt = materialsRepository.findById(studyMaterialsOpenAPI.getId());
        if (studyMaterialsOpt.isEmpty()) {
            return ResponseEntity
                    .notFound()
                    .build();
        }
        StudyMaterials updatedMaterials = materialsMapper.toStudyMaterials(studyMaterialsOpenAPI);
        List<Resource> files = studyMaterialsBody.getFiles();
        setMaterialsFileNamesAndSaveFiles(files, updatedMaterials);
        materialsRepository.save(updatedMaterials);
        return ResponseEntity
                .ok()
                .build();
    }

    @Transactional
    @Override
    public List<StudyMaterials> getMaterialsBySubjectId(Long subjectId) {
        Optional<Subject> subjectOpt = subjectRepository.findById(subjectId);
        if (subjectOpt.isEmpty()) {
            throw new NotFoundException(
                    String.format("No Materials for Subject Id %d Were Not Found", subjectId)
            );
        }
        Subject subject = subjectOpt.get();
        return subject.getStudyMaterials().stream().toList();
    }

    private void setMaterialsFileNamesAndSaveFiles(List<Resource> files,
                                                   StudyMaterials studyMaterials) {
        if (files.isEmpty()) {
            throw new BadRequestException("Files is empty");
        }
        Set<FileName> fileNames = files.stream()
                .map((file) -> {
                            FileName fileName = FileName.builder()
                                    .initialName(file.getFilename())
                                    .nameS3(fileService.generateMaterialsFileName(file.getFilename(), studyMaterials.getType()))
                                    .build();
                            fileService.save(getMaterialsFilePath(fileName), file);
                            return fileName;
                        }
                )
                .map(fileNameService::saveFileName)
                .collect(Collectors.toSet());
        studyMaterials.setAttachedFiles(fileNames);
    }

//    private String getMaterialsFilePath(FileName fileName, StudyMaterials studyMaterials) {
//        return String.format("%s%s/%s/%s",
//                studyMaterials.getSubject().getId(),
//                FileService.MATERIALS_PREFIX,
//                studyMaterials.getId(),
//                fileName.getNameS3());
//    }

    private String getMaterialsFilePath(FileName fileName) {
        return String.format("%s%s",
                FileService.MATERIALS_PREFIX,
                fileName.getNameS3());
    }

}
