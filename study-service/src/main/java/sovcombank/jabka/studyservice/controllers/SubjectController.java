package sovcombank.jabka.studyservice.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.sovcombank.openapi.api.SubjectApiDelegate;
import ru.sovcombank.openapi.model.SubjectOpenAPI;
import sovcombank.jabka.studyservice.services.interfaces.SubjectService;

import java.util.List;

@RestController
@RequestMapping("/api/study/subject")
@RequiredArgsConstructor
public class SubjectController implements SubjectApiDelegate {
    private final SubjectService subjectService;
    @PostMapping
    @Override
    public ResponseEntity<Void> createSubject(@RequestBody SubjectOpenAPI subjectOpenAPI) {
        return subjectService.createSubject(subjectOpenAPI);
    }

    @DeleteMapping("/{id}")
    @Override
    public ResponseEntity<Void> deleteSubject(@Valid @PathVariable(name = "id") Long id) {
        return subjectService.deleteSubject(id);
    }

    @GetMapping
    @Override
    @ResponseBody
    public ResponseEntity<List<SubjectOpenAPI>> getAllSubjects() {
        return subjectService.getAllSubjects();
    }

    @GetMapping("/{id}")
    @Override
    @ResponseBody
    public ResponseEntity<SubjectOpenAPI> getSubjectById(@Valid @PathVariable(name = "id")Long id) {
        return subjectService.getSubjectById(id);
    }

    @PutMapping
    @Override
    public ResponseEntity<Void> updateSubject(@RequestBody SubjectOpenAPI subjectOpenAPI) {
        return subjectService.updateSubject(subjectOpenAPI);
    }
}
