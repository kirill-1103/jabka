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
import sovcombank.jabka.studyservice.models.FileName;
import sovcombank.jabka.studyservice.models.StudyMaterials;
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

    @Override
    @GetMapping("/download/homework/{fileId}")
    public ResponseEntity<Resource> downloadHomeworkFileById(@PathVariable Long fileId) {
        String name = fileNameService.getNameByFileId(fileId);
        ResponseEntity<ByteArrayResource> responseEntity =
                fileService.getFileByPath(getHomeworkFilePath(name));

        return getFile(responseEntity);
    }

    @Override
    @GetMapping("/download/materials/{fileId}")
    public ResponseEntity<Resource> downloadMaterialsFileById(@PathVariable Long fileId) {
        String name = fileNameService.getNameByFileId(fileId);
        ResponseEntity<ByteArrayResource> responseEntity =
                fileService.getFileByPath(getMaterialsFilePath(name));

        return getFile(responseEntity);
    }

    private String getHomeworkFilePath(String fileName) {
        return String.format("%s%s", FileService.HOMEWORK_PREFIX, fileName);
    }

    private String getMaterialsFilePath(String fileName) {
        return String.format("%s%s",
                FileService.MATERIALS_PREFIX,
                fileName);
    }

    private String getMaterialsFilePath(FileName fileName, StudyMaterials studyMaterials) {
        return String.format("%s%s/%s/%s",
                studyMaterials.getSubject().getId(),
                FileService.MATERIALS_PREFIX,
                studyMaterials.getId(),
                fileName.getNameS3());
    }

    private ResponseEntity<Resource> getFile(ResponseEntity<ByteArrayResource> responseEntity){
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
