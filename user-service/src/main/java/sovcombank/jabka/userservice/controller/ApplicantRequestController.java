package sovcombank.jabka.userservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.sovcombank.openapi.api.ApplicantRequestApiDelegate;
import ru.sovcombank.openapi.model.ApplicantRequestOpenApi;
import sovcombank.jabka.userservice.mapper.ApplicantRequestMapper;
import sovcombank.jabka.userservice.service.interfaces.ApplicantRequestService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user/request")
public class ApplicantRequestController implements ApplicantRequestApiDelegate {
    private final ApplicantRequestService applicantRequestService;
    private final ApplicantRequestMapper applicantRequestMapper;

    @Override
    public ResponseEntity<ApplicantRequestOpenApi> addApplicantRequest(ApplicantRequestOpenApi applicantRequestOpenApi) {
        return ResponseEntity.ok(applicantRequestService.add(applicantRequestOpenApi)
                .map(applicantRequestMapper::toApplicantRequestOpenApi)
                .get());
    }

    @Override
    public ResponseEntity<ApplicantRequestOpenApi> applicantRequestByUserId(Long id) {
        return ResponseEntity.ok(applicantRequestService.findByUserId(id)
                .map(applicantRequestMapper::toApplicantRequestOpenApi)
                .get());
    }

    @Override
    public ResponseEntity<Void> deleteApplicantRequest(Long id) {
        applicantRequestService.delete(id);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<List<ApplicantRequestOpenApi>> getAllApplicantRequests() {
        return ResponseEntity.ok(applicantRequestService.getAll()
                .stream()
                .map(applicantRequestMapper::toApplicantRequestOpenApi)
                .collect(Collectors.toList()));
    }

    @Override
    public ResponseEntity<ApplicantRequestOpenApi> showApplicantRequestInfo(Long id) {
        return ResponseEntity.ok(applicantRequestService.findById(id)
                .map(applicantRequestMapper::toApplicantRequestOpenApi).get());
    }

    @Override
    public ResponseEntity<ApplicantRequestOpenApi> updateApplicantRequest(ApplicantRequestOpenApi applicantRequestOpenApi) {
        return ResponseEntity.ok(applicantRequestService.update(applicantRequestOpenApi)
                .map(applicantRequestMapper::toApplicantRequestOpenApi).get());
    }

}
