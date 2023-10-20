package sovcombank.jabka.studyservice.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.sovcombank.openapi.ApiException;
import ru.sovcombank.openapi.ApiResponse;
import ru.sovcombank.openapi.api.UserApi;
import ru.sovcombank.openapi.model.StudyGroupOpenAPI;
import ru.sovcombank.openapi.model.UserOpenApi;
import sovcombank.jabka.studyservice.exceptions.BadRequestException;
import sovcombank.jabka.studyservice.exceptions.NotFoundException;
import sovcombank.jabka.studyservice.mappers.StudyGroupMapper;
import sovcombank.jabka.studyservice.models.StudyGroup;
import sovcombank.jabka.studyservice.repositories.StudyGroupRepository;
import sovcombank.jabka.studyservice.services.interfaces.StudyGroupService;

import java.util.List;

import static sovcombank.jabka.studyservice.utils.ResponseApiUtils.okResponse;

@Service
@RequiredArgsConstructor
@Slf4j
public class StudyGroupServiceImpl implements StudyGroupService {
    private final StudyGroupRepository groupRepository;
    private final StudyGroupMapper groupMapper;

    private final UserApi userApi;

    @Transactional
    @Override
    public ResponseEntity<Void> createGroup(StudyGroupOpenAPI studyGroupOpenAPI) {
        if (groupRepository.existsByName(studyGroupOpenAPI.getName())) {
            throw new BadRequestException("Group with such name is already exists");
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
        StudyGroup group = groupRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Group with such id %d not found", id)));
        try {
            List<UserOpenApi> groupUsers = userApi.getUsersByGroupNumber(group.getName());
            groupUsers.forEach(user -> user.setGroup(null));
            ApiResponse<Void> response = userApi.updateUsersWithHttpInfo(groupUsers);
            if (!okResponse(response)) {
                throw new BadRequestException("Cannot update group's users");
            }
        } catch (ApiException e) {
            e.printStackTrace();
            throw new BadRequestException("Cannot get/update group's users. E:" + e.getMessage());
        }
        groupRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @Transactional
    @Override
    public ResponseEntity<List<StudyGroupOpenAPI>> getAllGroups() {
        List<StudyGroup> studyGroups = groupRepository.findAll();
        List<StudyGroupOpenAPI> studyGroupsOpenApi = groupMapper.toOpenAPI(studyGroups);
        studyGroupsOpenApi.forEach(this::addStudentsIdsInGroup);
        return ResponseEntity.ok(studyGroupsOpenApi);
    }

    @Transactional
    @Override
    public ResponseEntity<StudyGroupOpenAPI> getGroupById(Long id) {
        StudyGroup studyGroup = groupRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Groups with such id %d not found", id)));
        StudyGroupOpenAPI studyGroupOpenAPI = groupMapper.toOpenAPI(studyGroup);
        addStudentsIdsInGroup(studyGroupOpenAPI);
        return ResponseEntity.ok(studyGroupOpenAPI);
    }

    private void addStudentsIdsInGroup(StudyGroupOpenAPI studyGroupOpenAPI) {
        try {
            List<UserOpenApi> usersInGroup = userApi.getUsersByGroupNumber(studyGroupOpenAPI.getName());
            studyGroupOpenAPI.setStudentsIds(usersInGroup.stream()
                    .map(UserOpenApi::getId)
                    .toList()
            );
        } catch (ApiException e) {
            e.printStackTrace();
            throw new BadRequestException("Cannot load group's users");
        }
    }
}
