
package sovcombank.jabka.studyservice.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.sovcombank.openapi.api.ScheduleApiDelegate;
import ru.sovcombank.openapi.model.ScheduleOpenAPI;

import java.util.List;

@RestController
@RequestMapping("/api/study/schedule")
public class ScheduleController implements ScheduleApiDelegate {
    @Override
    public ResponseEntity<Void> createSchedule(ScheduleOpenAPI scheduleOpenAPI) {
        return ScheduleApiDelegate.super.createSchedule(scheduleOpenAPI);
    }

    @Override
    public ResponseEntity<Void> deleteSchedule(Long id) {
        return ScheduleApiDelegate.super.deleteSchedule(id);
    }

    @Override
    public ResponseEntity<List<ScheduleOpenAPI>> getAllSchedule() {
        return ScheduleApiDelegate.super.getAllSchedule();
    }

    @Override
    public ResponseEntity<ScheduleOpenAPI> getScheduleByGroup(Long id) {
        return ScheduleApiDelegate.super.getScheduleByGroup(id);
    }

    @Override
    public ResponseEntity<List<ScheduleOpenAPI>> getScheduleByProfessorId(Long professorId) {
        return ScheduleApiDelegate.super.getScheduleByProfessorId(professorId);
    }

    @Override
    public ResponseEntity<Void> updateSchedule(ScheduleOpenAPI scheduleOpenAPI) {
        return ScheduleApiDelegate.super.updateSchedule(scheduleOpenAPI);
    }
}
