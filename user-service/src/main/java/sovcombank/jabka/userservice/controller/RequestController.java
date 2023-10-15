package sovcombank.jabka.userservice.controller;

import org.springframework.http.ResponseEntity;
import ru.sovkombank.openapi.api.RequestApiDelegate;
import ru.sovkombank.openapi.model.JwtResponseOpenApi;
import ru.sovkombank.openapi.model.RequestOpenApi;

import java.util.List;

public class RequestController implements RequestApiDelegate {
    @Override
    public ResponseEntity<RequestOpenApi> addRequest(RequestOpenApi requestOpenApi) {
        return RequestApiDelegate.super.addRequest(requestOpenApi);
    }

    @Override
    public ResponseEntity<Void> deleteRequest(Long id) {
        return RequestApiDelegate.super.deleteRequest(id);
    }

    @Override
    public ResponseEntity<List<RequestOpenApi>> getAllRequests() {
        return RequestApiDelegate.super.getAllRequests();
    }

    @Override
    public ResponseEntity<RequestOpenApi> showRequestInfo(Long id) {
        return RequestApiDelegate.super.showRequestInfo(id);
    }

    @Override
    public ResponseEntity<JwtResponseOpenApi> updateRequest(RequestOpenApi requestOpenApi) {
        return RequestApiDelegate.super.updateRequest(requestOpenApi);
    }
}
