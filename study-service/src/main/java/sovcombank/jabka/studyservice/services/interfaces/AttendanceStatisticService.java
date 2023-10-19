package sovcombank.jabka.studyservice.services.interfaces;

import org.springframework.stereotype.Service;
import ru.sovcombank.openapi.model.AttendanceStatisticsOpenApi;
import sovcombank.jabka.studyservice.models.AttendanceStatistics;

import java.util.List;

@Service
public interface AttendanceStatisticService {
    void saveStatistics(List<AttendanceStatisticsOpenApi> attendanceStatisticsOpenApi);

    List<AttendanceStatistics> getStatisticsByGroupId(Long groupId);

    AttendanceStatistics getById(Long id);

    List<AttendanceStatistics> getStatisticsBySubjectId(Long subjectId);
}
