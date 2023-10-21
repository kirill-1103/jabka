package sovcombank.jabka.studyservice.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.sovcombank.openapi.model.StudyMaterialsOpenAPI;
import sovcombank.jabka.studyservice.models.StudyMaterials;

import java.util.List;

@Mapper(componentModel = "spring", uses = SubjectMapper.class)
public interface StudyMaterialsMapper {


    StudyMaterials toStudyMaterials(StudyMaterialsOpenAPI studyMaterialsOpenAPI);

    List<StudyMaterials> toStudyMaterials(List<StudyMaterialsOpenAPI> studyMaterialsOpenAPI);

    StudyMaterialsOpenAPI toOpenAPI(StudyMaterials studyMaterials);

    List<StudyMaterialsOpenAPI> toOpenAPI(List<StudyMaterials> studyMaterials);
}
