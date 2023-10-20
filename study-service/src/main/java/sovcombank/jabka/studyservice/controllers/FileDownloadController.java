package sovcombank.jabka.studyservice.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.sovcombank.openapi.api.FileDownloadApiDelegate;
import sovcombank.jabka.studyservice.exceptions.NotFoundException;
import sovcombank.jabka.studyservice.services.interfaces.FileNameService;
import sovcombank.jabka.studyservice.services.interfaces.FileService;

@RestController
@RequestMapping("/api/study")
@RequiredArgsConstructor
public class FileDownloadController implements FileDownloadApiDelegate {
    private final FileService fileService;
    private final FileNameService fileNameService;

    @GetMapping("/download/{fileId}")
    @Override
    public ResponseEntity<Resource> downloadFileById(Long fileId) {
        String path;
        try {
            path = fileNameService.getPathByFileId(fileId);
        } catch (NotFoundException e) {
            return ResponseEntity
                    .notFound()
                    .build();
        }
        Resource file = fileService.getFileByPath(path).getBody();
        return ResponseEntity
                .ok(file);
    }
}
