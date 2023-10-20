package sovcombank.jabka.studyservice.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.sovcombank.openapi.ApiException;
import ru.sovcombank.openapi.api.UserApi;
import ru.sovcombank.openapi.model.AttendanceStatisticsOpenApi;
import ru.sovcombank.openapi.model.ERoleOpenApi;
import ru.sovcombank.openapi.model.UserOpenApi;
import sovcombank.jabka.studyservice.exceptions.BadRequestException;
import sovcombank.jabka.studyservice.exceptions.NotFoundException;
import sovcombank.jabka.studyservice.mappers.AttendanceMapper;
import sovcombank.jabka.studyservice.models.AttendanceStatistics;
import sovcombank.jabka.studyservice.models.Schedule;
import sovcombank.jabka.studyservice.models.StudyGroup;
import sovcombank.jabka.studyservice.models.Subject;
import sovcombank.jabka.studyservice.repositories.AttendanceRepository;
import sovcombank.jabka.studyservice.repositories.ScheduleRepository;
import sovcombank.jabka.studyservice.repositories.StudyGroupRepository;
import sovcombank.jabka.studyservice.repositories.SubjectRepository;
import sovcombank.jabka.studyservice.services.interfaces.AttendanceStatisticService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class AttendanceStatisticServiceImpl implements AttendanceStatisticService {
    private final AttendanceRepository attendanceRepository;
    private final AttendanceMapper attendanceMapper;
    private final StudyGroupRepository groupRepository;
    private final ScheduleRepository scheduleRepository;

    private final SubjectRepository subjectRepository;
    private final UserApi userApi;

    //todo: добавить ендпоинт - статистика по расписанию - в контроллер

    @Override
    @Transactional
    public ResponseEntity<Void> saveStatistics(List<AttendanceStatisticsOpenApi> attendanceStatisticsOpenApi) {
        if (attendanceStatisticsOpenApi == null || attendanceStatisticsOpenApi.isEmpty()) {
            throw new BadRequestException("Attendance Statistics List is Empty");
        }
        //todo: проверить что юзеры существуют
        List<AttendanceStatistics> attendanceStatisticsList = attendanceMapper.toListAttendanceStatistics(attendanceStatisticsOpenApi);
        if (!allStudentsExists(attendanceStatisticsList)) {
            return ResponseEntity
                    .notFound()
                    .build();
        }
        attendanceRepository.saveAll(attendanceStatisticsList);
        return ResponseEntity
                .ok()
                .build();
    }

    @Override
    @Transactional
    public List<AttendanceStatistics> getStatisticsBySchedule(Long scheduleId) {
        Schedule schedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new NotFoundException(String.format("Schedule with id %d not found", scheduleId)));
        return schedule.getAttendanceStatistics().stream().toList();
    }

    @Override
    @Transactional
    public List<AttendanceStatistics> getStatisticsByGroupId(Long groupId) {
        StudyGroup group = groupRepository.findById(groupId)
                .orElseThrow(() -> new NotFoundException(String.format("Group with id %d not found", groupId)));
        List<Long> usersIds = new ArrayList<>(); //todo: получить юзеров этой группы, проверить статус код
        try {
            List<UserOpenApi> users = userApi.getAllUsers();
            String groupName = group.getName();
            usersIds = users.stream()
                    .filter(user -> user.getGroup().equals(groupName))
                    .map(UserOpenApi::getId)
                    .collect(Collectors.toList());
        } catch (ApiException e) {
            log.debug("An error occurred while fetching user information", e);
        }
        return attendanceRepository.findByStudentIdIn(usersIds);
    }

    @Override
    public AttendanceStatistics getById(Long id) {
        return attendanceRepository.findById(id)
                .orElseThrow(() -> new BadRequestException(String.format("Statistics with id %d not found", id)));
    }

    @Override
    public List<AttendanceStatistics> getStatisticsBySubjectId(Long subjectId) {
        Subject subject = subjectRepository.findById(subjectId)
                .orElseThrow(() -> new BadRequestException(String.format("Subject with id %d not found", subjectId)));
        return subject.getSchedule().stream()
                .flatMap(schedule -> schedule.getAttendanceStatistics().stream())
                .toList();

    }

    private boolean allStudentsExists(List<AttendanceStatistics> attendanceStatisticsList) {
        boolean foundNonExistentUser = false;
        List<Long> userIds = new ArrayList<>();
        for (AttendanceStatistics attendanceStatistics : attendanceStatisticsList) {
            Long studentId = attendanceStatistics.getStudentId();
            userIds.add(studentId);
        }
        List<UserOpenApi> userList;
        try {
            userList = userApi.getUsersByIds(userIds);
            for(UserOpenApi user : userList) {
                boolean userExistsByStudentId = user.getRoles() != null &&
                        user.getRoles()
                                .stream()
                                .anyMatch(role -> ERoleOpenApi.STUDENT.equals(role.getName()));
                if (!userExistsByStudentId) {
                    foundNonExistentUser = true;
                    throw new NotFoundException(
                            String.format("User with id %d wasn't found", user.getId()));
                }
            }
        } catch (ApiException e) {
            log.debug("An error occurred while fetching user information", e);
            foundNonExistentUser = true;
        }
        return !foundNonExistentUser;
    }
}
