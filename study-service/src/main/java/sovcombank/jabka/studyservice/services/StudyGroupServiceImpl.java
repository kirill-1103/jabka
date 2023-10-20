package sovcombank.jabka.studyservice.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
<<<<<<< HEAD
import ru.sovcombank.openapi.ApiException;
=======
>>>>>>> 2737e99 (empty)
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
import java.util.Optional;
import java.util.stream.Collectors;

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
<<<<<<< HEAD
        StudyGroup group = groupRepository.findById(id)
                .orElseThrow(()->new NotFoundException(String.format("Group with such id %d not found",id)));
        try {
            List<UserOpenApi> groupUsers = userApi.getUsersByGroupNumber(group.getName());
            groupUsers.forEach(user->user.setGroup(null));
        } catch (ApiException e) {
            log.error(e.getMessage());
            throw new BadRequestException("Cannot get/update group's users");
=======
        Optional<StudyGroup> studyGroupOpt = groupRepository.findById(id);
        //todo: получить студентов с этой группой, удалить у них группу, сохранить их



        if (studyGroupOpt.isEmpty()) {
            return ResponseEntity
                    .notFound()
                    .build();
>>>>>>> 2737e99 (empty)
        }
        groupRepository.deleteById(id);
        return null;
    }

    @Transactional
    @Override
    public ResponseEntity<List<StudyGroupOpenAPI>> getAllGroups() {
        List<StudyGroup> studyGroups = groupRepository.findAll();
        //todo: в группы вручную добавить id-шники студентов, которые в ней находятся
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
        //todo: в группу вручную добавить id-шники студентов, которые в ней находятся
        return studyGroupOpt
                .map(studyGroup -> ResponseEntity.ok(groupMapper.toOpenAPI(studyGroup)))
                .orElseGet
                        (
                                () -> ResponseEntity.notFound().build()
                        );
    }
}
