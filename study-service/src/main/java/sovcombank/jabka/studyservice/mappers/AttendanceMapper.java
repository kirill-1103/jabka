package sovcombank.jabka.studyservice.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.sovcombank.openapi.model.AttendanceStatisticsOpenApi;
import sovcombank.jabka.studyservice.models.AttendanceStatistics;

import java.util.List;

@Mapper(componentModel = "spring", uses = {ScheduleMapper.class})
public interface AttendanceMapper {
    @Mapping(target = "status", source = "attendanceStatus")
    AttendanceStatisticsOpenApi toOpenApi(AttendanceStatistics attendanceStatistics);


    @Mapping(target = "attendanceStatus", source = "status")
    AttendanceStatistics toAttendanceStatistics(AttendanceStatisticsOpenApi attendanceStatisticsOpenApi);

    @Mapping(target = "status", source = "attendanceStatus")
    List<AttendanceStatisticsOpenApi> toOpenApiList(List<AttendanceStatistics> list);


    @Mapping(target = "attendanceStatus", source = "status")
    List<AttendanceStatistics> toListAttendanceStatistics(List<AttendanceStatisticsOpenApi> list);
}
