package sovcombank.jabka.studyservice.controllers;

import org.springframework.http.ResponseEntity;
import ru.sovcombank.openapi.api.AttendanceStatisticsApiDelegate;
import ru.sovcombank.openapi.model.AttendanceStatisticsOpenApi;

import java.util.List;

public class AttendanceStatisticsController implements AttendanceStatisticsApiDelegate {
    @Override
    public ResponseEntity<Void> addStatistics(List<AttendanceStatisticsOpenApi> attendanceStatisticsOpenApi) {
        return AttendanceStatisticsApiDelegate.super.addStatistics(attendanceStatisticsOpenApi);
    }

    @Override
    public ResponseEntity<List<AttendanceStatisticsOpenApi>> getStatisticsByGroupId(Long groupId) {
        return AttendanceStatisticsApiDelegate.super.getStatisticsByGroupId(groupId);
    }

    @Override
    public ResponseEntity<AttendanceStatisticsOpenApi> getStatisticsById(Long id) {
        return AttendanceStatisticsApiDelegate.super.getStatisticsById(id);
    }

    @Override
    public ResponseEntity<List<AttendanceStatisticsOpenApi>> getStatisticsBySubjectId(Long subjectId) {
        return AttendanceStatisticsApiDelegate.super.getStatisticsBySubjectId(subjectId);
    }
}
