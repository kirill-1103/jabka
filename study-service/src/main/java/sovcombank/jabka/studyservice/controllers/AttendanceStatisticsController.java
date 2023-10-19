package sovcombank.jabka.studyservice.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;
import ru.sovcombank.openapi.api.AttendanceStatisticsApiDelegate;
import ru.sovcombank.openapi.model.AttendanceStatisticsOpenApi;
import ru.sovcombank.openapi.ApiException;
import ru.sovcombank.openapi.model.UserOpenApi;
import ru.sovcombank.openapi.ApiResponse;
import ru.sovcombank.openapi.api.UserApi;
import sovcombank.jabka.studyservice.exceptions.BadRequestException;
import sovcombank.jabka.studyservice.exceptions.NotFoundException;
import sovcombank.jabka.studyservice.mappers.AttendanceMapper;
import sovcombank.jabka.studyservice.models.AttendanceStatistics;
import sovcombank.jabka.studyservice.services.interfaces.AttendanceStatisticService;

import static sovcombank.jabka.studyservice.utils.ResponseApiUtils.okResponse;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/study/attendance-statistics")
@RequiredArgsConstructor
public class AttendanceStatisticsController implements AttendanceStatisticsApiDelegate {
    private final AttendanceStatisticService attendanceStatisticService;
    private final AttendanceMapper attendanceMapper;

    private final UserApi userApi;

    @Override
    @PostMapping
    public ResponseEntity<Void> addStatistics(@RequestBody List<AttendanceStatisticsOpenApi> attendanceStatisticsOpenApi) {
        attendanceStatisticService.saveStatistics(attendanceStatisticsOpenApi);
        return ResponseEntity.ok().build();
    }

    @Override
    @GetMapping("/group/{group_id}")
    public ResponseEntity<List<AttendanceStatisticsOpenApi>> getStatisticsByGroupId(@PathVariable("group_id") Long groupId) {
        List<AttendanceStatistics> statistics = attendanceStatisticService.getStatisticsByGroupId(groupId);
        return ResponseEntity.ok(getAttendanceOpenApiList(statistics));
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<AttendanceStatisticsOpenApi> getStatisticsById(@PathVariable Long id) {
        AttendanceStatistics attendanceStatistics = attendanceStatisticService.getById(id);
        AttendanceStatisticsOpenApi attendanceStatisticsOpenApi = attendanceMapper.toOpenApi(attendanceStatistics);
        Long userId = attendanceStatisticsOpenApi.getUserId();
        ApiResponse<UserOpenApi> userOpenApiResponse = null;
        try {
            userOpenApiResponse = userApi.showUserInfoWithHttpInfo(userId);
            if(!okResponse(userOpenApiResponse)){
                if(userOpenApiResponse.getStatusCode() == HttpStatus.NOT_FOUND.value()){
                    throw new NotFoundException("User in AttendanceStatistics with id"+id+" not found.");
                }
            }
        } catch (ApiException e) {
            e.printStackTrace();
            throw new BadRequestException(e.getMessage());
        }

        attendanceStatisticsOpenApi.setUser(userOpenApiResponse.getData());
        return ResponseEntity.ok(attendanceStatisticsOpenApi);
    }

    @Override
    @GetMapping("/subject/{subject_id}")
    public ResponseEntity<List<AttendanceStatisticsOpenApi>> getStatisticsBySubjectId(@PathVariable("subject_id") Long subjectId) {
        List<AttendanceStatistics> statistics = attendanceStatisticService.getStatisticsBySubjectId(subjectId);

        return ResponseEntity.ok(getAttendanceOpenApiList(statistics));
    }

    private List<AttendanceStatisticsOpenApi> getAttendanceOpenApiList(List<AttendanceStatistics> statistics){
        List<AttendanceStatisticsOpenApi> statisticsOpenApi = attendanceMapper.toOpenApiList(statistics);
        List<Long> userIds = statisticsOpenApi.stream().map(s -> s.getUserId()).toList();
        try {
            ApiResponse<List<UserOpenApi>> usersResponse = userApi.getUsersByIdsWithHttpInfo(userIds);
            if(!okResponse(usersResponse)){
                throw new BadRequestException();
            }
        } catch (ApiException e) {
            e.printStackTrace();
            throw new BadRequestException(e.getMessage());
        }
        List<UserOpenApi> users = null;
        for(int i = 0;i<userIds.size();i++){
            statisticsOpenApi.get(i).setUser(users.get(i));
        }
        return statisticsOpenApi;
    }
}
