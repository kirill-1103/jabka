package sovcombank.jabka.studyservice.mappers;

import org.mapstruct.Mapper;
import ru.sovcombank.openapi.model.HomeworkOpenAPI;
import sovcombank.jabka.studyservice.models.Homework;

import java.util.List;

@Mapper(componentModel = "spring")
public interface HomeworkMapper {
    Homework toHomework(HomeworkOpenAPI homeworkOpenAPI);

    List<Homework> toHomework(List<HomeworkOpenAPI> homeworkOpenAPI);

    HomeworkOpenAPI toOpenAPI(Homework homework);

    List<HomeworkOpenAPI> toOpenAPI(List<Homework> homework);
}
