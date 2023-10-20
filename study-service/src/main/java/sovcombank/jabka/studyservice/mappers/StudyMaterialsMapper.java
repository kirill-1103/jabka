package sovcombank.jabka.studyservice.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.sovcombank.openapi.model.StudyMaterialsOpenAPI;
import sovcombank.jabka.studyservice.models.StudyMaterials;

import java.util.List;

@Mapper(componentModel = "spring", uses = SubjectMapper.class)
public interface StudyMaterialsMapper {

//    @Mapping(target = "subject", source = "subject")
    StudyMaterials toStudyMaterials(StudyMaterialsOpenAPI studyMaterialsOpenAPI);

//    @Mapping(target = "subject", source = "subject")
    List<StudyMaterials> toStudyMaterials(List<StudyMaterialsOpenAPI> studyMaterialsOpenAPI);

//    @Mapping(target = "subject", source = "subject")
    StudyMaterialsOpenAPI toOpenAPI(StudyMaterials studyMaterials);

//    @Mapping(target = "subject", source = "subject")
    List<StudyMaterialsOpenAPI> toOpenAPI(List<StudyMaterials> studyMaterials);
}
