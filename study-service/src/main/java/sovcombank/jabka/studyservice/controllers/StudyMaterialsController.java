package sovcombank.jabka.studyservice.controllers;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.sovcombank.openapi.api.MaterialsApiDelegate;
import ru.sovcombank.openapi.model.StudyMaterialsOpenAPI;

import java.util.List;

@RestController
@RequestMapping("/api/study/materials")
public class StudyMaterialsController implements MaterialsApiDelegate {
    @PostMapping
    @Override
    public ResponseEntity<Void> createMaterials(@RequestBody StudyMaterialsOpenAPI studyMaterialsOpenAPI) {
        return MaterialsApiDelegate.super.createMaterials(studyMaterialsOpenAPI);
    }

    @DeleteMapping("/{id}")
    @Override
    public ResponseEntity<Void> deleteMaterials(@Valid @PathVariable(name = "id") Long id) {
        return MaterialsApiDelegate.super.deleteMaterials(id);
    }

    @GetMapping
    @Override
    @ResponseBody
    public ResponseEntity<List<StudyMaterialsOpenAPI>> getAllMaterials() {
        return MaterialsApiDelegate.super.getAllMaterials();
    }

    @GetMapping("/{id}")
    @Override
    @ResponseBody
    public ResponseEntity<StudyMaterialsOpenAPI> getMaterialsById(@Valid @PathVariable(name = "id") Long id) {
        return MaterialsApiDelegate.super.getMaterialsById(id);
    }

    @PutMapping
    @Override
    public ResponseEntity<Void> updateMaterials(@RequestBody StudyMaterialsOpenAPI studyMaterialsOpenAPI) {
        return MaterialsApiDelegate.super.updateMaterials(studyMaterialsOpenAPI);
    }
}
