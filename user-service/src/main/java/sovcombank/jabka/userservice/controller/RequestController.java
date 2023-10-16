package sovcombank.jabka.userservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.sovcombank.openapi.api.RequestApiDelegate;
import ru.sovcombank.openapi.model.RequestOpenApi;
import sovcombank.jabka.userservice.mapper.RequestMapper;
import sovcombank.jabka.userservice.service.interfaces.RequestService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user/request")
public class RequestController implements RequestApiDelegate {
    private final RequestService requestService;
    private final RequestMapper requestMapper;

    @Override
    public ResponseEntity<RequestOpenApi> addRequest(RequestOpenApi requestOpenApi) {
        return ResponseEntity.ok(requestService.addOne(requestOpenApi)
                .map(requestMapper::toRequestOpenApi)
                .get());
    }

    @Override
    public ResponseEntity<Void> deleteRequest(Long id) {
        requestService.delete(id);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<List<RequestOpenApi>> getAllRequests() {
        return ResponseEntity.ok(requestService.getAll()
                .stream()
                .map(requestMapper::toRequestOpenApi)
                .collect(Collectors.toList()));
    }

    @Override
    public ResponseEntity<RequestOpenApi> showRequestInfo(Long id) {
        return ResponseEntity.ok(requestService.getOneById(id)
                .map(requestMapper::toRequestOpenApi).get());
    }

    @Override
    public ResponseEntity<RequestOpenApi> updateRequest(RequestOpenApi requestOpenApi) {
        return ResponseEntity.ok(requestService.update(requestOpenApi).map(requestMapper::toRequestOpenApi).get());
    }
}
