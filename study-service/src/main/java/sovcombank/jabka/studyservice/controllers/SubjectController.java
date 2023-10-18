package sovcombank.jabka.studyservice.controllers;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.sovcombank.openapi.api.SubjectApiDelegate;
import ru.sovcombank.openapi.model.SubjectOpenAPI;

import java.util.List;

@RestController
@RequestMapping("/api/study/subject")
public class SubjectController implements SubjectApiDelegate {
    @PostMapping
    @Override
    public ResponseEntity<Void> createSubject(@RequestBody SubjectOpenAPI subjectOpenAPI) {
        return SubjectApiDelegate.super.createSubject(subjectOpenAPI);
    }

    @DeleteMapping("/{id}")
    @Override
    public ResponseEntity<Void> deleteSubject(@Valid @PathVariable(name = "id") Long id) {
        return SubjectApiDelegate.super.deleteSubject(id);
    }

    @GetMapping
    @Override
    @ResponseBody
    public ResponseEntity<List<SubjectOpenAPI>> getAllSubjects() {
        return SubjectApiDelegate.super.getAllSubjects();
    }

    @GetMapping("/{id}")
    @Override
    @ResponseBody
    public ResponseEntity<SubjectOpenAPI> getSubjectById(@Valid @PathVariable(name = "id")Long id) {
        return SubjectApiDelegate.super.getSubjectById(id);
    }

    @PutMapping
    @Override
    public ResponseEntity<Void> updateSubject(@RequestBody SubjectOpenAPI subjectOpenAPI) {
        return SubjectApiDelegate.super.updateSubject(subjectOpenAPI);
    }
}
