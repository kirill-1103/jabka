package sovcombank.jabka.studyservice.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.sovcombank.openapi.model.AttendanceStatisticsOpenApi;
import sovcombank.jabka.studyservice.models.AttendanceStatistics;

import java.util.List;

@Mapper(componentModel = "spring", uses = SubjectMapper.class)
public interface AttendanceMapper {
//    @Mapping(target = "schedule", source = "schedule")
    AttendanceStatisticsOpenApi toOpenApi(AttendanceStatistics attendanceStatistics);

//    @Mapping(target = "schedule", source = "schedule")
    AttendanceStatistics toAttendanceStatistics(AttendanceStatisticsOpenApi attendanceStatisticsOpenApi);

//    @Mapping(target = "schedule", source = "schedule")
    List<AttendanceStatisticsOpenApi> toOpenApiList(List<AttendanceStatistics> list);

//    @Mapping(target = "schedule", source = "schedule")
    List<AttendanceStatistics> toListAttendanceStatistics(List<AttendanceStatisticsOpenApi> list);
}
