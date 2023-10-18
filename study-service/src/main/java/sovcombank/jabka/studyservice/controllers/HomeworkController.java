package sovcombank.jabka.studyservice.controllers;


import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.sovcombank.openapi.api.HomeworkApiDelegate;
import ru.sovcombank.openapi.model.HomeworkOpenAPI;
import ru.sovcombank.openapi.model.TaskEvaluationOpenAPI;

import java.util.List;

@RestController
@RequestMapping("/api/study")
public class HomeworkController implements HomeworkApiDelegate {
    @PostMapping("/materials/{materialsId}/homework")
    @Override
    public ResponseEntity<Void> createHomework(
            @Valid @PathVariable(name = "materialsId") Long materialsId,
            @RequestBody HomeworkOpenAPI homework,
            @RequestBody List<MultipartFile> file
    ) {
        return HomeworkApiDelegate.super.createHomework(materialsId, homework, file);
    }

    @DeleteMapping("/materials/{materialsId}/homework/{homeworkId}")
    @Override
    public ResponseEntity<Void> deleteHomework(
            @Valid @PathVariable(name = "materialsId") Long materialsId,
            @Valid @PathVariable(name = "homeworkId") Long homeworkId
    ) {
        return HomeworkApiDelegate.super.deleteHomework(materialsId, homeworkId);
    }

    @GetMapping("/materials/{materialsId}/homework/all")
    @Override
    @ResponseBody
    public ResponseEntity<List<HomeworkOpenAPI>> getAllHomeworksByMaterials(
            @Valid @PathVariable(name = "materialsId") Long materialsId
    ) {
        return HomeworkApiDelegate.super.getAllHomeworksByMaterials(materialsId);
    }

    @GetMapping("/materials/{materialsId}/homework/{homeworkId}")
    @Override
    @ResponseBody
    public ResponseEntity<HomeworkOpenAPI> getHomeworkById(
            @Valid @PathVariable(name = "materialsId") Long materialsId,
            @Valid @PathVariable(name = "homeworkId") Long homeworkId
    ) {
        return HomeworkApiDelegate.super.getHomeworkById(materialsId, homeworkId);
    }

    @GetMapping("/materials/{materialsId}/homework/student/{studentId}")
    @Override
    @ResponseBody
    public ResponseEntity<HomeworkOpenAPI> getHomeworkByStudentAndMaterials(
            @Valid @PathVariable(name = "materialsId") Long materialsId,
            @Valid @PathVariable(name = "studentId") Long studentId
    ) {
        return HomeworkApiDelegate.super.getHomeworkByStudentAndMaterials(materialsId, studentId);
    }

    @GetMapping("/homework/student/{studentId}")
    @Override
    @ResponseBody
    public ResponseEntity<List<HomeworkOpenAPI>> getHomeworkByStudentId(
            @Valid @PathVariable(name = "studentId") Long studentId
    ) {
        return HomeworkApiDelegate.super.getHomeworkByStudentId(studentId);
    }

    @PutMapping("/materials/{materialsId}/homework/student/{studentId}")
    @Override
    public ResponseEntity<Void> gradeHomeworkByStudent(
            @Valid @PathVariable(name = "materialsId") Long materialsId,
            @Valid @PathVariable(name = "studentId") Long studentId,
            @RequestBody TaskEvaluationOpenAPI taskEvaluationOpenAPI
    ) {
        return HomeworkApiDelegate.super.gradeHomeworkByStudent(materialsId, studentId, taskEvaluationOpenAPI);
    }

    @PutMapping("/materials/{materialsId}/homework")
    @Override
    public ResponseEntity<Void> updateHomework(
            @Valid @PathVariable(name = "materialsId") Long materialsId,
            @RequestBody HomeworkOpenAPI homework,
            @RequestBody List<MultipartFile> file
    ) {
        return HomeworkApiDelegate.super.updateHomework(materialsId, homework, file);

    }
}
