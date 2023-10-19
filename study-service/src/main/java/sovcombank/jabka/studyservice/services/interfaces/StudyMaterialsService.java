package sovcombank.jabka.studyservice.services.interfaces;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import ru.sovcombank.openapi.model.StudyMaterialsBody;
import ru.sovcombank.openapi.model.StudyMaterialsOpenAPI;
import sovcombank.jabka.studyservice.models.StudyMaterials;

import java.util.List;

@Service
public interface StudyMaterialsService {
    ResponseEntity<Void> createMaterials(StudyMaterialsBody studyMaterialsBody);
    ResponseEntity<Void> deleteMaterials(Long id);
    List<StudyMaterials> getAllMaterials();
    StudyMaterials getMaterialsById(Long id);
    ResponseEntity<Void> updateMaterials(StudyMaterialsBody studyMaterialsBody);
    List<StudyMaterials> getMaterialsBySubjectId(Long subjectId);
}
