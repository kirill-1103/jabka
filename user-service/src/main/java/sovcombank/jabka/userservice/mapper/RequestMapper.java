package sovcombank.jabka.userservice.mapper;

import org.mapstruct.Mapper;
import ru.sovcombank.openapi.model.RequestOpenApi;
import sovcombank.jabka.userservice.model.Request;

@Mapper(componentModel = "spring")

public interface RequestMapper {
    Request toRequest(RequestOpenApi requestOpenApi);

    RequestOpenApi toRequestOpenApi(Request request);
}
