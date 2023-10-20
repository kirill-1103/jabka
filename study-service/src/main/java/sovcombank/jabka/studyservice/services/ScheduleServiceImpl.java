package sovcombank.jabka.studyservice.services;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.sovcombank.openapi.ApiException;
import ru.sovcombank.openapi.ApiResponse;
import ru.sovcombank.openapi.api.UserApi;
import ru.sovcombank.openapi.model.ScheduleOpenAPI;
import ru.sovcombank.openapi.model.UserOpenApi;
import sovcombank.jabka.studyservice.exceptions.BadRequestException;
import sovcombank.jabka.studyservice.exceptions.NotFoundException;
import sovcombank.jabka.studyservice.mappers.ScheduleMapper;
import sovcombank.jabka.studyservice.models.Schedule;
import sovcombank.jabka.studyservice.models.Subject;
import sovcombank.jabka.studyservice.repositories.ScheduleRepository;
import sovcombank.jabka.studyservice.repositories.StudyGroupRepository;
import sovcombank.jabka.studyservice.repositories.SubjectRepository;
import sovcombank.jabka.studyservice.services.interfaces.ScheduleService;

import static sovcombank.jabka.studyservice.utils.ResponseApiUtils.okResponse;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduleServiceImpl implements ScheduleService {
    private final ScheduleRepository scheduleRepository;
    private final ScheduleMapper scheduleMapper;
    private final StudyGroupRepository groupRepository;
    private final SubjectRepository subjectRepository;
    private final UserApi userApi;

    @Override
    @Transactional
    public void createSchedule(ScheduleOpenAPI openAPI) {
        openAPI.setId(null);
        try {
            scheduleRepository.save(scheduleMapper.toSchedule(openAPI));
        } catch (Exception e) {
            e.printStackTrace();
            throw new BadRequestException(e.getMessage());
        }
    }

    @Override
    @Transactional
    public void deleteScheduleById(Long id) {
        Schedule schedule = scheduleRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Schedule with id %d not found", id)));
        scheduleRepository.delete(schedule);
    }

    @Override
    @Transactional
    public List<Schedule> getAll() {
        return scheduleRepository.findAll();
    }

    @Override
    @Transactional
    public List<Schedule> getByGroupId(Long id) {
        groupRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Group with id %d not found", id)));
        List<Subject> subjects = subjectRepository.findByStudyGroupId(id);
        return subjects.stream()
                .flatMap(subject -> subject.getSchedule().stream())
                .toList();
    }

    @Override
    @Transactional
    public List<Schedule> getByProfessorId(Long professorId) {
        try {
            ApiResponse<UserOpenApi> usersResponse = userApi.showUserInfoWithHttpInfo(professorId);
            if (!okResponse(usersResponse)) {
                if (usersResponse.getStatusCode() == HttpStatus.NOT_FOUND.value()) {
                    throw new NotFoundException(String.format("Professor with id %d not found", professorId));
                }
                throw new BadRequestException("Get Schedule by professor failed");
            }
        } catch (ApiException e) {
            e.printStackTrace();
            throw new BadRequestException(e.getMessage());
        }
        return scheduleRepository.findByProfessorProfessorId(professorId);
    }

    @Override
    @Transactional
    public void updateSchedule(ScheduleOpenAPI scheduleOpenAPI) {
        if (scheduleOpenAPI.getId() == null) {
            throw new BadRequestException("Id is null");
        }
        scheduleRepository.findById(scheduleOpenAPI.getId())
                .orElseThrow(() -> new NotFoundException("Schedule with such id is not exists"));
        try {
            scheduleRepository.save(scheduleMapper.toSchedule(scheduleOpenAPI));
        } catch (Exception e) {
            throw new BadRequestException("Cannot update Schedule. Exception: " + e.getMessage());
        }
    }
}
