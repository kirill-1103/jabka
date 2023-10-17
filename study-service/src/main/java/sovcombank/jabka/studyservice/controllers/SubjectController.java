package sovcombank.jabka.studyservice.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.sovcombank.openapi.api.SubjectApiDelegate;
import ru.sovcombank.openapi.model.SubjectOpenAPI;

import java.util.List;

@RestController
@RequestMapping("/api/study/subject")
public class SubjectController implements SubjectApiDelegate {
    @Override
    public ResponseEntity<Void> createSubject(SubjectOpenAPI subjectOpenAPI) {
        return SubjectApiDelegate.super.createSubject(subjectOpenAPI);
    }

    @Override
    public ResponseEntity<Void> deleteSubject(Long id) {
        return SubjectApiDelegate.super.deleteSubject(id);
    }

    @Override
    public ResponseEntity<List<SubjectOpenAPI>> getAllSubjects() {
        return SubjectApiDelegate.super.getAllSubjects();
    }

    @Override
    public ResponseEntity<SubjectOpenAPI> getSubjectById(Long id) {
        return SubjectApiDelegate.super.getSubjectById(id);
    }

    @Override
    public ResponseEntity<Void> updateHSubject(SubjectOpenAPI subjectOpenAPI) {
        return SubjectApiDelegate.super.updateHSubject(subjectOpenAPI);
    }
}
