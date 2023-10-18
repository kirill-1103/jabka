package sovcombank.jabka.studyservice.controllers;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.sovcombank.openapi.api.GroupApiDelegate;
import ru.sovcombank.openapi.model.StudyGroupOpenAPI;

import java.util.List;

@RestController
@RequestMapping("/api/study/group")
public class StudyGroupController implements GroupApiDelegate {

    @PostMapping
    @Override
    public ResponseEntity<Void> createGroup(@RequestBody StudyGroupOpenAPI studyGroupOpenAPI) {
        return GroupApiDelegate.super.createGroup(studyGroupOpenAPI);
    }

    @DeleteMapping("/{id}")
    @Override
    public ResponseEntity<Void> deleteGroupById(@Valid @PathVariable(name = "id") Long id) {
        return GroupApiDelegate.super.deleteGroupById(id);
    }

    @GetMapping
    @Override
    @ResponseBody
    public ResponseEntity<List<StudyGroupOpenAPI>> getAllGroups() {
        return GroupApiDelegate.super.getAllGroups();
    }

    @GetMapping("/{id}")
    @Override
    public ResponseEntity<StudyGroupOpenAPI> getGroupById(@Valid @PathVariable(name = "id") Long id) {
        return GroupApiDelegate.super.getGroupById(id);
    }
}
