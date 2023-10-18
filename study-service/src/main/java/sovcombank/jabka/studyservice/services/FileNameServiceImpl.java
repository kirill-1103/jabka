package sovcombank.jabka.studyservice.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sovcombank.jabka.studyservice.exceptions.BadRequestException;
import sovcombank.jabka.studyservice.models.FileName;
import sovcombank.jabka.studyservice.repositories.FileNameRepository;
import sovcombank.jabka.studyservice.services.interfaces.FileNameService;

@Service
@RequiredArgsConstructor
public class FileNameServiceImpl implements FileNameService {
    private final FileNameRepository fileNameRepository;

    @Override
    public FileName saveFileName(FileName fileName) {
        if (fileName.getInitialName() == null || fileName.getNameS3() == null
                || fileName.getInitialName().isBlank() || fileName.getNameS3().isBlank()) {
            throw new BadRequestException("file name is blank");
        }
        return fileNameRepository.save(fileName);
    }
}
