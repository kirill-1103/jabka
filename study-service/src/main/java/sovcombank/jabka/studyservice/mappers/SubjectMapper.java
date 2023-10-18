package sovcombank.jabka.studyservice.mappers;

import org.mapstruct.Mapper;
import ru.sovcombank.openapi.model.SubjectOpenAPI;
import sovcombank.jabka.studyservice.models.Subject;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SubjectMapper {
    Subject toSubject(SubjectOpenAPI subjectOpenAPI);

    List<Subject> toSubject(List<SubjectOpenAPI> subjectOpenAPI);

    SubjectOpenAPI toOpenAPI(Subject subject);

    List<SubjectOpenAPI> toOpenAPI(List<Subject> subject);
}
