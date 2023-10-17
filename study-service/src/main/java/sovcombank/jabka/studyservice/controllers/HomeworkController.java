package sovcombank.jabka.studyservice.controllers;


import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ru.sovcombank.openapi.api.HomeworkApiDelegate;
import ru.sovcombank.openapi.model.HomeworkOpenAPI;

import java.util.List;

@RestController
@RequestMapping("/api/study/homework")
public class HomeworkController implements HomeworkApiDelegate {


    @PostMapping
    @Override
    public ResponseEntity<Void> createHomework(HomeworkOpenAPI homework, List<MultipartFile> file) {
        return HomeworkApiDelegate.super.createHomework(homework, file);
    }



      @DeleteMapping
    @Override
    public ResponseEntity<Void> deleteHomework(Long id) {
        return HomeworkApiDelegate.super.deleteHomework(id);
    }


    @GetMapping
    @Override
    public ResponseEntity<List<HomeworkOpenAPI>> getAllHomeworks() {
        return HomeworkApiDelegate.super.getAllHomeworks();
    }
    @GetMapping("/{id}")
    @Override
    public ResponseEntity<HomeworkOpenAPI> getHomeworkById(
            @Valid @PathVariable(name = "id") Long id
    ) {
        return HomeworkApiDelegate.super.getHomeworkById(id);
    }

    @GetMapping("/materials/{materialsId}")
    @Override
    public ResponseEntity<List<HomeworkOpenAPI>> getHomeworkByMaterialsId(
            @Valid @PathVariable(name = "materialsId") Long materialsId
    ) {
        return HomeworkApiDelegate.super.getHomeworkByMaterialsId(materialsId);
    }

    @GetMapping("/student/{studentId}")
    @Override
    public ResponseEntity<List<HomeworkOpenAPI>> getHomeworkByStudentId(
            @Valid @PathVariable(name = "studentId")Long studentId
    ) {
        return HomeworkApiDelegate.super.getHomeworkByStudentId(studentId);
    }

    @PutMapping
    @Override
    public ResponseEntity<Void> updateHomework(HomeworkOpenAPI homework, List<MultipartFile> file) {
        return HomeworkApiDelegate.super.updateHomework(homework, file);

    }
}
