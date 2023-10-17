package sovcombank.jabka.studyservice.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.sovcombank.openapi.api.MaterialsApiDelegate;
import ru.sovcombank.openapi.model.StudyMaterialsOpenAPI;

import java.util.List;

@RestController
@RequestMapping("/api/study/materials")
public class StudyMaterialsController implements MaterialsApiDelegate {
    @Override
    public ResponseEntity<Void> createMaterials(StudyMaterialsOpenAPI studyMaterialsOpenAPI) {
        return MaterialsApiDelegate.super.createMaterials(studyMaterialsOpenAPI);
    }

    @Override
    public ResponseEntity<Void> deleteMaterials(Long id) {
        return MaterialsApiDelegate.super.deleteMaterials(id);
    }

    @Override
    public ResponseEntity<List<StudyMaterialsOpenAPI>> getAllMaterials() {
        return MaterialsApiDelegate.super.getAllMaterials();
    }

    @Override
    public ResponseEntity<StudyMaterialsOpenAPI> getMaterialsById(Long id) {
        return MaterialsApiDelegate.super.getMaterialsById(id);
    }

    @Override
    public ResponseEntity<Void> updateMaterials(StudyMaterialsOpenAPI studyMaterialsOpenAPI) {
        return MaterialsApiDelegate.super.updateMaterials(studyMaterialsOpenAPI);
    }
}
