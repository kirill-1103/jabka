package sovcombank.jabka.studyservice.services.interfaces;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface FileService {
    String MATERIALS_PREFIX = "materials/";

    String HOMEWORK_PREFIX = "homework/";

    String TEST_PREFIX = "test/";

    ResponseEntity<ByteArrayResource> getFileByPath(String path);

    void save(String path, MultipartFile multipartFile);

    void removeFileByPath(String path);
}
