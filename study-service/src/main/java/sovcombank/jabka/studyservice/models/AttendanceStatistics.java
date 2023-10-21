package sovcombank.jabka.studyservice.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NonNull;
import sovcombank.jabka.studyservice.models.enums.AttendanceStatus;

@Data
@Entity
@Table(name = "attendance")
public class AttendanceStatistics {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false, name = "student_id")
    @NonNull
    private Long studentId;

    @ManyToOne
    private Schedule schedule;

    @NonNull
    @Column(nullable = false, name = "attendance_status")
    @Enumerated(EnumType.STRING)
    private AttendanceStatus attendanceStatus;
}
