package sovcombank.jabka.studyservice.services;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.sovcombank.openapi.model.StudyMaterialsBody;
import ru.sovcombank.openapi.model.StudyMaterialsOpenAPI;
import sovcombank.jabka.studyservice.mappers.StudyMaterialsMapper;
import sovcombank.jabka.studyservice.models.StudyMaterials;
import sovcombank.jabka.studyservice.repositories.StudyMaterialsRepository;
import sovcombank.jabka.studyservice.services.interfaces.StudyMaterialsService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


//todo: не забудь что тут с файлами еще дофига работы (сохранять, удалять из бд FileName и в S3)
//todo: мб при сохранении надоп проверять, что Subject, для которого добавляется материалс существует
@Service
@RequiredArgsConstructor
public class StudyMaterialsServiceImpl implements StudyMaterialsService {
    private final StudyMaterialsMapper materialsMapper;
    private final StudyMaterialsRepository materialsRepository;

    @Transactional
    @Override
    public ResponseEntity<Void> createMaterials(StudyMaterialsBody studyMaterialsBody) {
        StudyMaterialsOpenAPI studyMaterialsOpenAPI = studyMaterialsBody.getStudyMaterials();
        StudyMaterials studyMaterials = materialsMapper.toStudyMaterials(studyMaterialsOpenAPI);
        //todo: проверь, что тут норм будет мапиться
        materialsRepository.save(studyMaterials);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .build();
    }

    @Transactional
    @Override
    public ResponseEntity<Void> deleteMaterials(Long id) {
        Optional<StudyMaterials> studyMaterials = materialsRepository.findById(id);
        if (studyMaterials.isEmpty()) {
            return ResponseEntity
                    .notFound()
                    .build();
        }
        materialsRepository.deleteById(id);
        return ResponseEntity
                .ok()
                .build();
    }

    @Transactional
    @Override
    public ResponseEntity<List<StudyMaterialsOpenAPI>> getAllMaterials() {
        List<StudyMaterials> studyMaterials = materialsRepository.findAll();
        if (studyMaterials.isEmpty()) {
            return ResponseEntity
                    .notFound()
                    .build();
        }
        return ResponseEntity.ok(studyMaterials
                .stream()
                .map(materialsMapper::toOpenAPI)
                .collect(Collectors.toList())
        );
    }

    @Transactional
    @Override
    public ResponseEntity<StudyMaterialsOpenAPI> getMaterialsById(Long id) {
        Optional<StudyMaterials> studyMaterialsOpt = materialsRepository.findById(id);
        return studyMaterialsOpt
                .map(studyMaterials -> ResponseEntity.ok(materialsMapper.toOpenAPI(studyMaterials)))
                .orElseGet
                        (
                                () -> ResponseEntity.notFound().build()
                        );
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
        materialsRepository.save(updatedMaterials);
        List<Resource> files = studyMaterialsBody.getFiles();
        if (!files.isEmpty()) {

        }
        return ResponseEntity
                .ok()
                .build();
    }
}
