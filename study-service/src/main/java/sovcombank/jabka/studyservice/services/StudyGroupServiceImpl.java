package sovcombank.jabka.studyservice.services;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.sovcombank.openapi.model.StudyGroupOpenAPI;
import sovcombank.jabka.studyservice.mappers.StudyGroupMapper;
import sovcombank.jabka.studyservice.models.StudyGroup;
import sovcombank.jabka.studyservice.repositories.StudyGroupRepository;
import sovcombank.jabka.studyservice.services.interfaces.StudyGroupService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StudyGroupServiceImpl implements StudyGroupService {
    private final StudyGroupRepository groupRepository;
    private final StudyGroupMapper groupMapper;

    @Transactional
    @Override
    public ResponseEntity<Void> createGroup(StudyGroupOpenAPI studyGroupOpenAPI) {
        if (groupRepository.existsByGroupName(studyGroupOpenAPI.getName())) {
            return ResponseEntity
                    .badRequest()
                    .build();
        }
        StudyGroup studyGroup = groupMapper.toStudyGroup(studyGroupOpenAPI);
        groupRepository.save(studyGroup);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .build();
    }

    @Transactional
    @Override
    public ResponseEntity<Void> deleteGroupById(Long id) {
        Optional<StudyGroup> studyGroupOpt = groupRepository.findById(id);
        if (studyGroupOpt.isEmpty()) {
            return ResponseEntity
                    .notFound()
                    .build();
        }
        groupRepository.deleteById(id);
        return null;
    }

    @Transactional
    @Override
    public ResponseEntity<List<StudyGroupOpenAPI>> getAllGroups() {
        List<StudyGroup> studyGroups = groupRepository.findAll();
        if (studyGroups.isEmpty()) {
            return ResponseEntity
                    .notFound()
                    .build();
        }
        return ResponseEntity.ok(studyGroups
                .stream()
                .map(groupMapper::toOpenAPI)
                .collect(Collectors.toList())
        );
    }

    @Transactional
    @Override
    public ResponseEntity<StudyGroupOpenAPI> getGroupById(Long id) {
        Optional<StudyGroup> studyGroupOpt = groupRepository.findById(id);
        return studyGroupOpt
                .map(studyGroup -> ResponseEntity.ok(groupMapper.toOpenAPI(studyGroup)))
                .orElseGet
                        (
                                () -> ResponseEntity.notFound().build()
                        );
    }
}
