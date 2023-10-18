package sovcombank.jabka.studyservice.controllers;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.sovcombank.openapi.api.HomeworkApiDelegate;
import ru.sovcombank.openapi.model.HomeworkOpenAPI;
import sovcombank.jabka.studyservice.mappers.HomeworkMapper;
import sovcombank.jabka.studyservice.models.FileName;
import sovcombank.jabka.studyservice.models.Homework;
import sovcombank.jabka.studyservice.services.interfaces.HomeworkService;

import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/study/homework")
@RequiredArgsConstructor
public class HomeworkController implements HomeworkApiDelegate {


    private final HomeworkService homeworkService;
    private final HomeworkMapper homeworkMapper;

    @PostMapping("/materials/{materialsId}/homework")
    @Override
    public ResponseEntity<Void> createHomework(
            @Valid @PathVariable(name = "materialsId") Long materialsId,
            @RequestBody HomeworkOpenAPI homework,
            @RequestBody List<MultipartFile> files
    ) {
        homeworkService.createHomework(materialsId, homework, files);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/materials/{materialsId}/homework/{homeworkId}")
    @Override
    public ResponseEntity<Void> deleteHomework(
            @Valid @PathVariable(name = "materialsId") Long materialsId,
            @Valid @PathVariable(name = "homeworkId") Long homeworkId
    ) {
        homeworkService.deleteHomework(materialsId, homeworkId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/materials/{materialsId}/homework/all")
    @Override
    @ResponseBody
    public ResponseEntity<List<HomeworkOpenAPI>> getAllHomeworksByMaterials(
            @Valid @PathVariable(name = "materialsId") Long materialsId
    ) {
        List<Homework> homeworks = homeworkService.getAllHomeworkByMaterialsId(materialsId);
        return ResponseEntity.ok(this.homeworksOpenApiByHomeworks(homeworks));
    }

    @GetMapping("/materials/{materialsId}/homework/{homeworkId}")
    @Override
    @ResponseBody
    public ResponseEntity<HomeworkOpenAPI> getHomeworkById(
            @Valid @PathVariable(name = "materialsId") Long materialsId,
            @Valid @PathVariable(name = "homeworkId") Long homeworkId
    ) {
        Homework homework = homeworkService.getHomeworkById(homeworkId);
        HomeworkOpenAPI homeworkOpenAPI = homeworkMapper.toOpenAPI(homework);
        homeworkOpenAPI.setFilesNames(getStringFileNames(homework.getFileNames()));
        return ResponseEntity.ok(homeworkOpenAPI);
    }

    @GetMapping("/materials/{materialsId}/homework/student/{studentId}")
    @Override
    @ResponseBody
    public ResponseEntity<HomeworkOpenAPI> getHomeworkByStudentAndMaterials(
            @Valid @PathVariable(name = "materialsId") Long materialsId,
            @Valid @PathVariable(name = "studentId") Long studentId
    ) {
        Homework homework = homeworkService.getHomeworkByStudentAndMaterials(materialsId, studentId);
        HomeworkOpenAPI homeworkOpenAPI = homeworkMapper.toOpenAPI(homework);
        homeworkOpenAPI.setFilesNames(getStringFileNames(homework.getFileNames()));
        return ResponseEntity.ok(homeworkOpenAPI);
    }

    @GetMapping("/homework/student/{studentId}")
    @Override
    @ResponseBody
    public ResponseEntity<List<HomeworkOpenAPI>> getHomeworkByStudentId(
            @Valid @PathVariable(name = "studentId") Long studentId
    ) {
        List<Homework> homeworks = homeworkService.getHomeworksByStudentId(studentId);
        return ResponseEntity.ok(this.homeworksOpenApiByHomeworks(homeworks));
    }

    @PutMapping("/materials/{materialsId}/homework")
    @Override
    public ResponseEntity<Void> updateHomework(
            @Valid @PathVariable(name = "materialsId") Long materialsId,
            @RequestBody HomeworkOpenAPI homework,
            @RequestBody List<MultipartFile> file
    ) {
        homeworkService.updateHomework(homework, file);
        return ResponseEntity.ok().build();
    }

    private List<String> getStringFileNames(Set<FileName> fileNames) {
        return fileNames.stream()
                .map(FileName::getNameS3)
                .collect(Collectors.toList());
    }

    private List<HomeworkOpenAPI> homeworksOpenApiByHomeworks(List<Homework> homeworks) {
        List<HomeworkOpenAPI> homeworksOpenAPI = homeworkMapper.toOpenAPI(homeworks);
        Iterator<Homework> iteratorHomework = homeworks.listIterator();
        Iterator<HomeworkOpenAPI> iteratorHomeworkOpenApi = homeworksOpenAPI.listIterator();
        while (iteratorHomework.hasNext()) {
            Homework homework = iteratorHomework.next();
            HomeworkOpenAPI homeworkOpenAPI = iteratorHomeworkOpenApi.next();
            homeworkOpenAPI.setFilesNames(getStringFileNames(homework.getFileNames()));
        }
        return homeworksOpenAPI;
    }
}
