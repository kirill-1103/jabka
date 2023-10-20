package sovcombank.jabka.studyservice.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.sovcombank.openapi.model.ScheduleOpenAPI;
import sovcombank.jabka.studyservice.models.Schedule;

import java.util.List;

@Mapper(componentModel = "spring", uses = SubjectMapper.class)
public interface ScheduleMapper {

//    @Mapping(target = "subject", source = "subject")
    Schedule toSchedule(ScheduleOpenAPI scheduleOpenAPI);

//    @Mapping(target = "subject", source = "subject")
    List<Schedule> toSchedule(List<ScheduleOpenAPI> scheduleOpenAPI);

//    @Mapping(target = "subject", source = "subject")
    ScheduleOpenAPI toOpenAPI(Schedule schedule);

//    @Mapping(target = "subject", source = "subject")
    List<ScheduleOpenAPI> toOpenAPI(List<Schedule> schedule);
}
