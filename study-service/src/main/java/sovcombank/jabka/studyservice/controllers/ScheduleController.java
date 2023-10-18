
package sovcombank.jabka.studyservice.controllers;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.sovcombank.openapi.api.ScheduleApiDelegate;
import ru.sovcombank.openapi.model.ScheduleOpenAPI;

import java.util.List;

@RestController
@RequestMapping("/api/study/schedule")
public class ScheduleController implements ScheduleApiDelegate {

    @PostMapping
    @Override
    public ResponseEntity<Void> createSchedule(@RequestBody ScheduleOpenAPI scheduleOpenAPI) {
        return ScheduleApiDelegate.super.createSchedule(scheduleOpenAPI);
    }

    @DeleteMapping("/{id}")
    @Override
    public ResponseEntity<Void> deleteSchedule(@Valid @PathVariable(name = "id") Long id) {
        return ScheduleApiDelegate.super.deleteSchedule(id);
    }

    @GetMapping
    @Override
    @ResponseBody
    public ResponseEntity<List<ScheduleOpenAPI>> getAllSchedule() {
        return ScheduleApiDelegate.super.getAllSchedule();
    }

    @GetMapping("/{id}")
    @Override
    @ResponseBody
    public ResponseEntity<ScheduleOpenAPI> getScheduleByGroup(@Valid @PathVariable(name = "id") Long id) {
        return ScheduleApiDelegate.super.getScheduleByGroup(id);
    }

    @GetMapping("/professor/{professorId}")
    @Override
    @ResponseBody
    public ResponseEntity<List<ScheduleOpenAPI>> getScheduleByProfessorId(
            @Valid @PathVariable(name = "professorId") Long professorId
    ) {
        return ScheduleApiDelegate.super.getScheduleByProfessorId(professorId);
    }

    @PutMapping
    @Override
    public ResponseEntity<Void> updateSchedule(@RequestBody ScheduleOpenAPI scheduleOpenAPI) {
        return ScheduleApiDelegate.super.updateSchedule(scheduleOpenAPI);
    }
}
