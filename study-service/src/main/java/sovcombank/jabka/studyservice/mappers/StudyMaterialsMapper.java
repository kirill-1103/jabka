package sovcombank.jabka.studyservice.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.sovcombank.openapi.model.StudyMaterialsOpenAPI;
import sovcombank.jabka.studyservice.models.StudyMaterials;

import java.util.List;

@Mapper(componentModel = "spring", uses = {SubjectMapper.class, SubjectIdMapper.class})
public interface StudyMaterialsMapper {


    @Mapping(target = "subject", source = "subjectId")
    StudyMaterials toStudyMaterials(StudyMaterialsOpenAPI studyMaterialsOpenAPI);

    @Mapping(target = "subject", source = "subjectId")
    List<StudyMaterials> toStudyMaterials(List<StudyMaterialsOpenAPI> studyMaterialsOpenAPI);

    @Mapping(target = "subjectId", source = "subject")
    StudyMaterialsOpenAPI toOpenAPI(StudyMaterials studyMaterials);

    @Mapping(target = "subjectId", source = "subject")
    List<StudyMaterialsOpenAPI> toOpenAPI(List<StudyMaterials> studyMaterials);
}
