package sovcombank.jabka.studyservice.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.sovcombank.openapi.api.MaterialsApiDelegate;
import ru.sovcombank.openapi.model.StudyMaterialsBody;
import ru.sovcombank.openapi.model.StudyMaterialsOpenAPI;
import sovcombank.jabka.studyservice.services.interfaces.StudyMaterialsService;

import java.util.List;

@RestController
@RequestMapping("/api/study/materials")
@RequiredArgsConstructor
public class StudyMaterialsController implements MaterialsApiDelegate {
    private final StudyMaterialsService studyMaterialsService;

    //todo: нужен ендпоинт получения всех материалов, которые есть в предемете
    @PostMapping
    @Override
    public ResponseEntity<Void> createMaterials(@RequestBody StudyMaterialsBody studyMaterialsBody
    ) {
        return studyMaterialsService.createMaterials(studyMaterialsBody);
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
    public ResponseEntity<Void> updateMaterials(@RequestBody StudyMaterialsBody studyMaterialsBody) {
        return studyMaterialsService.updateMaterials(studyMaterialsBody);
    }
}
