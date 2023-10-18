package sovcombank.jabka.studyservice.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.ResponseBytes;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;
import sovcombank.jabka.studyservice.services.interfaces.FileService;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/study/test")
@RequiredArgsConstructor
public class TestController {

    private final FileService fileService;

    @PostMapping
    public void post(MultipartFile file) throws IOException {
        fileService.save(
                String.format("%s%s",FileService.TEST_PREFIX,file.getOriginalFilename()),
                file
        );
    }

    @GetMapping("/{name}")
    public ResponseEntity<ByteArrayResource> getFile(@PathVariable String name){
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
}
