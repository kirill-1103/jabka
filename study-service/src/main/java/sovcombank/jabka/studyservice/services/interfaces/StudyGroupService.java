package sovcombank.jabka.studyservice.services.interfaces;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.sovcombank.openapi.ApiException;
import ru.sovcombank.openapi.model.StudyGroupOpenAPI;

import java.util.List;

@Service
public interface StudyGroupService {
    ResponseEntity<Void> createGroup(StudyGroupOpenAPI studyGroupOpenAPI);
    ResponseEntity<Void> deleteGroupById(Long id) throws ApiException;
    ResponseEntity<List<StudyGroupOpenAPI>> getAllGroups();
    ResponseEntity<StudyGroupOpenAPI> getGroupById(Long id);
}
