package sovcombank.jabka.studyservice.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.sovcombank.openapi.api.GroupApiDelegate;
import ru.sovcombank.openapi.model.StudyGroupOpenAPI;

import java.util.List;

@RestController
@RequestMapping("/api/study/group")
public class StudyGroupController implements GroupApiDelegate {
    @Override
    public ResponseEntity<Void> createGroup(StudyGroupOpenAPI studyGroupOpenAPI) {
        return GroupApiDelegate.super.createGroup(studyGroupOpenAPI);
    }

    @Override
    public ResponseEntity<Void> deleteGroupById(Long id) {
        return GroupApiDelegate.super.deleteGroupById(id);
    }

    @Override
    public ResponseEntity<List<StudyGroupOpenAPI>> getAllGroups() {
        return GroupApiDelegate.super.getAllGroups();
    }

    @Override
    public ResponseEntity<StudyGroupOpenAPI> getGroupById(Long id) {
        return GroupApiDelegate.super.getGroupById(id);
    }
}
