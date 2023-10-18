package sovcombank.jabka.studyservice.services.interfaces;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.sovcombank.openapi.model.StudyMaterialsOpenAPI;

import java.util.List;

@Service
public interface StudyMaterialsService {
    ResponseEntity<Void> createMaterials(StudyMaterialsOpenAPI studyMaterialsOpenAPI);
    ResponseEntity<Void> deleteMaterials(Long id);
    ResponseEntity<List<StudyMaterialsOpenAPI>> getAllMaterials();
    ResponseEntity<StudyMaterialsOpenAPI> getMaterialsById(Long id);
    ResponseEntity<Void> updateMaterials(StudyMaterialsOpenAPI studyMaterialsOpenAPI);
}
