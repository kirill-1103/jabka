package sovcombank.jabka.userservice.service.interfaces;

import org.springframework.transaction.annotation.Transactional;
import ru.sovcombank.openapi.model.RequestOpenApi;
import sovcombank.jabka.userservice.model.Request;

import java.util.List;
import java.util.Optional;

public interface RequestService {
    Optional<Request> addOne(RequestOpenApi request);

    Optional<Request> getOneById(Long id);

    List<Request> getAll();

    Optional<Request> update(RequestOpenApi requestOpenApi);

    void delete(Long id);


    @Transactional
    Optional<Request> byUserId(Long id);
}
