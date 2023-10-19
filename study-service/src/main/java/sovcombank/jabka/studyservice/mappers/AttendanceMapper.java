package sovcombank.jabka.studyservice.mappers;

import org.mapstruct.Mapper;
import ru.sovcombank.openapi.model.AttendanceStatisticsOpenApi;
import sovcombank.jabka.studyservice.models.AttendanceStatistics;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AttendanceMapper {
    AttendanceStatisticsOpenApi toOpenApi(AttendanceStatistics attendanceStatistics);
    AttendanceStatistics toAttendanceStatistics(AttendanceStatisticsOpenApi attendanceStatisticsOpenApi);
    List<AttendanceStatisticsOpenApi> toOpenApiList(List<AttendanceStatistics> list);
    List<AttendanceStatistics> toListAttendanceStatistics (List<AttendanceStatisticsOpenApi> list);
}
