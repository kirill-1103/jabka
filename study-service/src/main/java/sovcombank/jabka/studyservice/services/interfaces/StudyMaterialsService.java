package sovcombank.jabka.studyservice.services.interfaces;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.sovcombank.openapi.model.StudyMaterialsBody;
import ru.sovcombank.openapi.model.StudyMaterialsOpenAPI;

import java.util.List;

@Service
public interface StudyMaterialsService {
    ResponseEntity<Void> createMaterials(StudyMaterialsBody studyMaterialsBody);
    ResponseEntity<Void> deleteMaterials(Long id);
    ResponseEntity<List<StudyMaterialsOpenAPI>> getAllMaterials();
    ResponseEntity<StudyMaterialsOpenAPI> getMaterialsById(Long id);
    ResponseEntity<Void> updateMaterials(StudyMaterialsBody studyMaterialsBody);
}
