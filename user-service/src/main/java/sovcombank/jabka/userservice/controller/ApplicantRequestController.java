package sovcombank.jabka.userservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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
    @PostMapping
    public ResponseEntity<ApplicantRequestOpenApi> addApplicantRequest(@RequestBody  ApplicantRequestOpenApi applicantRequestOpenApi) {
        return ResponseEntity.ok(applicantRequestService.add(applicantRequestOpenApi)
                .map(applicantRequestMapper::toApplicantRequestOpenApi)
                .get());
    }

    @Override
    @GetMapping("/user/{id}")
    public ResponseEntity<ApplicantRequestOpenApi> applicantRequestByUserId(@PathVariable  Long id) {
        return ResponseEntity.ok(applicantRequestService.findByUserId(id)
                .map(applicantRequestMapper::toApplicantRequestOpenApi)
                .get());
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteApplicantRequest(@PathVariable  Long id) {
        applicantRequestService.delete(id);
        return ResponseEntity.ok().build();
    }

    @Override
    @GetMapping
    public ResponseEntity<List<ApplicantRequestOpenApi>> getAllApplicantRequests() {
        return ResponseEntity.ok(applicantRequestService.getAll()
                .stream()
                .map(applicantRequestMapper::toApplicantRequestOpenApi)
                .collect(Collectors.toList()));
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<ApplicantRequestOpenApi> showApplicantRequestInfo(@PathVariable Long id) {
        return ResponseEntity.ok(applicantRequestService.findById(id)
                .map(applicantRequestMapper::toApplicantRequestOpenApi).get());
    }

    @Override
    @PutMapping
    public ResponseEntity<ApplicantRequestOpenApi> updateApplicantRequest(@RequestBody ApplicantRequestOpenApi applicantRequestOpenApi) {
        return ResponseEntity.ok(applicantRequestService.update(applicantRequestOpenApi)
                .map(applicantRequestMapper::toApplicantRequestOpenApi).get());
    }

}
