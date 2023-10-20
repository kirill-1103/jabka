package sovcombank.jabka.studyservice.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.sovcombank.openapi.api.FileDownloadApiDelegate;
import sovcombank.jabka.studyservice.exceptions.NotFoundException;
import sovcombank.jabka.studyservice.repositories.StudyMaterialsRepository;
import sovcombank.jabka.studyservice.services.interfaces.FileNameService;
import sovcombank.jabka.studyservice.services.interfaces.FileService;

@RestController
@RequestMapping("/api/study")
@RequiredArgsConstructor
public class FileDownloadController implements FileDownloadApiDelegate {
    private final FileService fileService;
    private final FileNameService fileNameService;
    private final StudyMaterialsRepository materialsRepository;

    @GetMapping("/download/{fileId}")
    @Override
    public ResponseEntity<Resource> downloadFileById(
            @Valid @PathVariable(name = "fileId") Long fileId
    ) {
        String path;
        try {
            path = fileNameService.getPathByFileId(fileId);
        } catch (NotFoundException e) {
            return ResponseEntity
                    .notFound()
                    .build();
        }

        ResponseEntity<ByteArrayResource> responseEntity = fileService.getFileByPath(path);
        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            ByteArrayResource byteArrayResource = responseEntity.getBody();
            HttpHeaders headers = new HttpHeaders();
            headers.putAll(responseEntity.getHeaders());

            Resource resource = new ByteArrayResource(byteArrayResource.getByteArray());
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .headers(headers)
                    .body(resource);
        } else {
            return ResponseEntity
                    .status(responseEntity.getStatusCode())
                    .headers(responseEntity.getHeaders())
                    .build();
        }
    }

//    @GetMapping("/download/{materialsId}/all")
//    //@Override
//    public ResponseEntity<List<Resource>> downloadAllFilesBySubject(
//            @Valid @PathVariable(name = "materialsId") Long materialsId
//    ){
//        List<String> paths = new ArrayList<>();
//        Optional<StudyMaterials> materialsOpt = materialsRepository.findById(materialsId);
//        if(materialsOpt.isEmpty()){
//            throw new NotFoundException(String.format("Study Materials With Id %d Wasn't Found", materialsId));
//        }
//        StudyMaterials materials = materialsOpt.get();
//        materials.getFileNames()
//                .stream()
//                .map(FileName::getNameS3)
//                .forEach(paths::add);
//        List<ByteArrayResource> allFiles = paths.stream()
//                .map(fileService::getFileByPath)
//                .collect(Collectors.toList());
//        return ResponseEntity.ok().build();
//    }
}
