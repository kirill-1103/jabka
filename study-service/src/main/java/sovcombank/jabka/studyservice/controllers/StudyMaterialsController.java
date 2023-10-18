package sovcombank.jabka.studyservice.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.sovcombank.openapi.api.MaterialsApiDelegate;
import ru.sovcombank.openapi.model.StudyMaterialsOpenAPI;
import sovcombank.jabka.studyservice.services.interfaces.StudyMaterialsService;

import java.util.List;

@RestController
@RequestMapping("/api/study/materials")
@RequiredArgsConstructor
public class StudyMaterialsController implements MaterialsApiDelegate {
    private final StudyMaterialsService studyMaterialsService;
    @PostMapping
    @Override
    public ResponseEntity<Void> createMaterials(@RequestBody StudyMaterialsOpenAPI studyMaterialsOpenAPI) {
        return studyMaterialsService.createMaterials(studyMaterialsOpenAPI);
    }

    @DeleteMapping("/{id}")
    @Override
    public ResponseEntity<Void> deleteMaterials(@Valid @PathVariable(name = "id") Long id) {
        return studyMaterialsService.deleteMaterials(id);
    }

    @GetMapping
    @Override
    @ResponseBody
    public ResponseEntity<List<StudyMaterialsOpenAPI>> getAllMaterials() {
        return studyMaterialsService.getAllMaterials();
    }

    @GetMapping("/{id}")
    @Override
    @ResponseBody
    public ResponseEntity<StudyMaterialsOpenAPI> getMaterialsById(@Valid @PathVariable(name = "id") Long id) {
        return studyMaterialsService.getMaterialsById(id);
    }

    @PutMapping
    @Override
    public ResponseEntity<Void> updateMaterials(@RequestBody StudyMaterialsOpenAPI studyMaterialsOpenAPI) {
        return studyMaterialsService.updateMaterials(studyMaterialsOpenAPI);
    }
}
