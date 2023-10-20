package sovcombank.jabka.studyservice.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.sovcombank.openapi.api.MaterialsApiDelegate;
import ru.sovcombank.openapi.model.StudyMaterialsBody;
import ru.sovcombank.openapi.model.StudyMaterialsOpenAPI;
import sovcombank.jabka.studyservice.mappers.StudyMaterialsMapper;
import sovcombank.jabka.studyservice.models.FileName;
import sovcombank.jabka.studyservice.models.StudyMaterials;
import sovcombank.jabka.studyservice.services.interfaces.StudyMaterialsService;

import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/study/materials")
@RequiredArgsConstructor
public class StudyMaterialsController implements MaterialsApiDelegate {
    private final StudyMaterialsService studyMaterialsService;
    private final StudyMaterialsMapper materialsMapper;

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
    public ResponseEntity<List<StudyMaterialsOpenAPI>> getAllMaterials() {
        List<StudyMaterials> studyMaterials = studyMaterialsService.getAllMaterials();
        return ResponseEntity
                .ok(toMaterialsOpenApi(studyMaterials));
    }

    @GetMapping("/{id}")
    @Override
    public ResponseEntity<StudyMaterialsOpenAPI> getMaterialsById(
            @Valid @PathVariable(name = "id") Long id
    ) {
        StudyMaterials studyMaterials = studyMaterialsService.getMaterialsById(id);
        return ResponseEntity.ok(toMaterialsOpenApi(studyMaterials));
    }

    @PutMapping
    @Override
    public ResponseEntity<Void> updateMaterials(@RequestBody StudyMaterialsBody studyMaterialsBody) {
        return studyMaterialsService.updateMaterials(studyMaterialsBody);
    }

    @GetMapping("/subject/{subjectId}")
    @Override
    public ResponseEntity<List<StudyMaterialsOpenAPI>> getMaterialsBySubjectId(
            @Valid @PathVariable(name = "id") Long subjectId
    ) {
        List<StudyMaterials> materials = studyMaterialsService.getMaterialsBySubjectId(subjectId);
        return ResponseEntity.ok(toMaterialsOpenApi(materials));
    }

    private List<StudyMaterialsOpenAPI> toMaterialsOpenApi(List<StudyMaterials> materials) {
        List<StudyMaterialsOpenAPI> materialsOpenAPI = materialsMapper.toOpenAPI(materials);
        Iterator<StudyMaterials> iteratorMaterials = materials.listIterator();
        Iterator<StudyMaterialsOpenAPI> iteratorMaterialsOpenApi = materialsOpenAPI.listIterator();
        while (iteratorMaterials.hasNext()) {
            StudyMaterials studyMaterials = iteratorMaterials.next();
            StudyMaterialsOpenAPI studyMaterialsOpenAPI = iteratorMaterialsOpenApi.next();
            studyMaterialsOpenAPI.setFilesId(getIdsFileNames(studyMaterials.getFileNames()));
            studyMaterialsOpenAPI.setFilesNames(getStringFileNames(studyMaterials.getFileNames()));
        }
        return materialsOpenAPI;
    }

    private StudyMaterialsOpenAPI toMaterialsOpenApi(StudyMaterials materials) {
        StudyMaterialsOpenAPI materialsOpenAPI = materialsMapper.toOpenAPI(materials);
        materialsOpenAPI.setFilesId(getIdsFileNames(materials.getFileNames()));
        materialsOpenAPI.setFilesNames(getStringFileNames(materials.getFileNames()));
        return materialsOpenAPI;
    }

    private List<String> getStringFileNames(Set<FileName> fileNames) {
        return fileNames.stream()
                .map(FileName::getNameS3)
                .collect(Collectors.toList());
    }

    private List<Long> getIdsFileNames(Set<FileName> fileNames) {
        return fileNames.stream()
                .map(FileName::getId)
                .collect(Collectors.toList());
    }

}
