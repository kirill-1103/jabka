package sovcombank.jabka.studyservice.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.sovcombank.openapi.model.HomeworkOpenAPI;
import sovcombank.jabka.studyservice.models.Homework;

import java.time.OffsetDateTime;
import java.util.Date;
import java.util.List;

@Mapper(componentModel = "spring", uses = {DateMapper.class, SubjectMapper.class})
public interface HomeworkMapper {
//    @Mapping(target = "task", source = "task")
    @Mapping(target = "date", source = "date", dateFormat = "yyyy-MM-dd HH:mm:ss")
    Homework toHomework(HomeworkOpenAPI homeworkOpenAPI);

//    @Mapping(target = "task", source = "task")
    @Mapping(target = "date", source = "date", dateFormat = "yyyy-MM-dd HH:mm:ss")
    List<Homework> toHomework(List<HomeworkOpenAPI> homeworkOpenAPI);

//    @Mapping(target = "task", source = "task")
    @Mapping(target = "date", source = "date", dateFormat = "yyyy-MM-dd'T'HH:mm:ssXXX")
    HomeworkOpenAPI toOpenAPI(Homework homework);

//    @Mapping(target = "task", source = "task")
    @Mapping(target = "date", source = "date", dateFormat = "yyyy-MM-dd'T'HH:mm:ssXXX")
    List<HomeworkOpenAPI> toOpenAPI(List<Homework> homework);

}
