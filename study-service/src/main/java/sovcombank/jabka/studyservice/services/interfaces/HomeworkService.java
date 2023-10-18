package sovcombank.jabka.studyservice.services.interfaces;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.sovcombank.openapi.model.HomeworkOpenAPI;

import java.util.List;

@Service
public interface HomeworkService {
    void createHomework(Long materialsId,
                        HomeworkOpenAPI homework,
                        List<MultipartFile> files);
}
