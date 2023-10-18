package sovcombank.jabka.studyservice.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NonNull;
import sovcombank.jabka.studyservice.models.enums.AttendanceStatus;

@Data
@Entity
@Table (name = "attendance")
public class AttendanceStatistics {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    @NonNull
    private Long studentId;

    @ManyToOne
    @JoinColumn(name = "schedule_id")
    private Schedule schedule;

    @NonNull
    @Column(nullable = false)
    private AttendanceStatus attendanceStatus;
}
