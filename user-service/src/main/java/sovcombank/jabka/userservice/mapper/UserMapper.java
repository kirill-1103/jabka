package sovcombank.jabka.userservice.mapper;

import org.mapstruct.Mapper;
import ru.sovcombank.openapi.model.SignupRequestOpenApi;
import ru.sovcombank.openapi.model.UserOpenApi;
import sovcombank.jabka.userservice.model.UserEntity;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserEntity toUser(UserOpenApi userOpenApi);
    UserOpenApi toUserOpenApi(UserEntity user);

    UserEntity toUser(SignupRequestOpenApi signupRequestOpenApi);

}
