package sovcombank.jabka.studyservice.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.sovcombank.openapi.user.ApiException;
import ru.sovcombank.openapi.user.model.UserOpenApi;
import ru.sovcombank.openapi.user.api.UserApi;
import sovcombank.jabka.studyservice.services.interfaces.FileService;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/study/test")
@RequiredArgsConstructor
public class TestController {

    private final FileService fileService;

    private final UserApi userApi;

    @PostMapping
    public void post(MultipartFile file) throws IOException {
        fileService.save(
                String.format("%s%s",FileService.TEST_PREFIX,file.getOriginalFilename()),
                file
        );
    }

    @GetMapping("/{name}")
    public ResponseEntity<ByteArrayResource> getFile(@PathVariable String name) throws Exception {
        return fileService.getFileByPath(
                String.format("%s%s",FileService.TEST_PREFIX,name)
        );
    }

    @DeleteMapping("/{name}")
    public void delete(@PathVariable String name){
        fileService.removeFileByPath(
                String.format("%s%s",FileService.TEST_PREFIX,name)
        );
    }

    @GetMapping("/user/all")
    public void userOpenApi() throws ApiException {
        var a = userApi.getAllUsersWithHttpInfo();
        return;
    }
}
