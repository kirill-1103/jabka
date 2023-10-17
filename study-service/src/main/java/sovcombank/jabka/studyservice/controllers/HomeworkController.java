package sovcombank.jabka.studyservice.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.sovcombank.openapi.api.HomeworkApiDelegate;
import ru.sovcombank.openapi.model.HomeworkOpenAPI;

import java.util.List;

@RestController
@RequestMapping("/api/study/homework")
public class HomeworkController implements HomeworkApiDelegate {
    @Override
    public ResponseEntity<Void> createHomework(HomeworkOpenAPI homeworkOpenAPI) {
        return HomeworkApiDelegate.super.createHomework(homeworkOpenAPI);
    }

    @Override
    public ResponseEntity<Void> deleteHomework(Long id) {
        return HomeworkApiDelegate.super.deleteHomework(id);
    }

    @Override
    public ResponseEntity<List<HomeworkOpenAPI>> getAllHomeworks() {
        return HomeworkApiDelegate.super.getAllHomeworks();
    }

    @Override
    public ResponseEntity<HomeworkOpenAPI> getHomeworkById(Long id) {
        return HomeworkApiDelegate.super.getHomeworkById(id);
    }

    @Override
    public ResponseEntity<List<HomeworkOpenAPI>> getHomeworkByMaterialsId(Long materialsId) {
        return HomeworkApiDelegate.super.getHomeworkByMaterialsId(materialsId);
    }

    @Override
    public ResponseEntity<List<HomeworkOpenAPI>> getHomeworkByStudentId(Long studentId) {
        return HomeworkApiDelegate.super.getHomeworkByStudentId(studentId);
    }

    @Override
    public ResponseEntity<Void> updateHomework(HomeworkOpenAPI homeworkOpenAPI) {
        return HomeworkApiDelegate.super.updateHomework(homeworkOpenAPI);
    }
}
