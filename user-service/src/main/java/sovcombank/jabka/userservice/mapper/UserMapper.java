package sovcombank.jabka.userservice.mapper;

import org.mapstruct.Mapper;
import ru.sovcombank.openapi.model.UserOpenApi;
import sovcombank.jabka.userservice.model.User;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toUser(UserOpenApi userOpenApi);
    UserOpenApi toUserOpenApi(User user);
}
