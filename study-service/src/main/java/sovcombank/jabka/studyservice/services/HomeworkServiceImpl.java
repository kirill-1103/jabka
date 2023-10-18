package sovcombank.jabka.studyservice.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.sovcombank.openapi.model.HomeworkOpenAPI;
import sovcombank.jabka.studyservice.exceptions.BadRequestException;
import sovcombank.jabka.studyservice.mappers.HomeworkMapper;
import sovcombank.jabka.studyservice.models.Homework;
import sovcombank.jabka.studyservice.models.StudyMaterials;
import sovcombank.jabka.studyservice.repositories.HomeworkRepository;
import sovcombank.jabka.studyservice.repositories.StudyMaterialsRepository;
import sovcombank.jabka.studyservice.services.interfaces.HomeworkService;
import sovcombank.jabka.studyservice.services.interfaces.StudyMaterialsService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HomeworkServiceImpl implements HomeworkService {
    private final HomeworkRepository homeworkRepository;
    private final StudyMaterialsRepository studyMaterialsRepository;

    private final HomeworkMapper homeworkMapper;
    @Override
    @Transactional
    public void createHomework(Long materialsId, HomeworkOpenAPI homeworkOpenApi, List<MultipartFile> files) {
        StudyMaterials studyMaterials =
                studyMaterialsRepository.findById(materialsId).orElseThrow(()->
                        new BadRequestException(String.format("Materials with id %d not found",materialsId)));

        Homework homework = homeworkMapper.toHomework

        studyMaterials.getHomeworks().add();



    }
}
