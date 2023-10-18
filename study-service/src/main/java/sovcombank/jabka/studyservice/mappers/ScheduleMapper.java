package sovcombank.jabka.studyservice.mappers;

import org.mapstruct.Mapper;
import ru.sovcombank.openapi.model.ScheduleOpenAPI;
import sovcombank.jabka.studyservice.models.Schedule;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ScheduleMapper {
    Schedule toSchedule(ScheduleOpenAPI scheduleOpenAPI);

    List<Schedule> toSchedule(List<ScheduleOpenAPI> scheduleOpenAPI);

    ScheduleOpenAPI toOpenAPI(Schedule schedule);

    List<ScheduleOpenAPI> toOpenAPI(List<Schedule> schedule);
}
