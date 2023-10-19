package sovcombank.jabka.studyservice.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.sovcombank.openapi.model.AttendanceStatisticsOpenApi;
import sovcombank.jabka.studyservice.exceptions.BadRequestException;
import sovcombank.jabka.studyservice.exceptions.NotFoundException;
import sovcombank.jabka.studyservice.mappers.AttendanceMapper;
import sovcombank.jabka.studyservice.models.AttendanceStatistics;
import sovcombank.jabka.studyservice.models.StudyGroup;
import sovcombank.jabka.studyservice.models.Subject;
import sovcombank.jabka.studyservice.repositories.AttendanceRepository;
import sovcombank.jabka.studyservice.repositories.StudyGroupRepository;
import sovcombank.jabka.studyservice.repositories.SubjectRepository;
import sovcombank.jabka.studyservice.services.interfaces.AttendanceStatisticService;
import sovcombank.jabka.studyservice.services.interfaces.StudyGroupService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AttendanceStatisticServiceImpl implements AttendanceStatisticService {
    private final AttendanceRepository attendanceRepository;
    private final AttendanceMapper attendanceMapper;
    private final StudyGroupRepository groupRepository;

    private final SubjectRepository subjectRepository;

    //todo: добавить ендпоинт - статистика по расписанию - в контроллер

    @Override
    @Transactional
    public void saveStatistics(List<AttendanceStatisticsOpenApi> attendanceStatisticsOpenApi) {
        if(attendanceStatisticsOpenApi == null || attendanceStatisticsOpenApi.isEmpty()){
            throw new BadRequestException("Attendance Statistics List is Empty");
        }
        //todo: проверить что юзеры существуют
        List<AttendanceStatistics> attendanceStatistics = attendanceMapper.toListAttendanceStatistics(attendanceStatisticsOpenApi);
        attendanceRepository.saveAll(attendanceStatistics);
    }

    @Override
    @Transactional
    public List<AttendanceStatistics> getStatisticsByGroupId(Long groupId) {
        StudyGroup group = groupRepository.findById(groupId)
                .orElseThrow(()-> new NotFoundException(String.format("Group with id %d not found",groupId)));
        List<Long> usersIds = null; //todo: получить юзеров этой группы, проверить статус код
        return attendanceRepository.findByStudentIdIn(usersIds);
    }

    @Override
    public AttendanceStatistics getById(Long id) {
        return attendanceRepository.findById(id)
                .orElseThrow(()->new BadRequestException(String.format("Statistics with id %d not found",id)));
    }

    @Override
    public List<AttendanceStatistics> getStatisticsBySubjectId(Long subjectId) {
        Subject subject = subjectRepository.findById(subjectId)
                .orElseThrow(()->new BadRequestException(String.format("Subject with id %d not found",subjectId)));
        return subject.getSchedule().stream()
                .flatMap(schedule -> schedule.getAttendanceStatistics().stream())
                .toList();

    }
}
