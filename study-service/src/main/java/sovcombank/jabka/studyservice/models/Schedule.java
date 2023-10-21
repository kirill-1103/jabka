package sovcombank.jabka.studyservice.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import sovcombank.jabka.studyservice.models.enums.ClassFormat;

import java.util.Date;
import java.util.Set;

@Entity
@Data
@EqualsAndHashCode
@Table(name = "schedule")
public class Schedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateTime;
    @ManyToOne
    private StudyGroup group;
    @ManyToOne
    @JoinColumn(name="subject_id",nullable = false)
    private Subject subject;
    @Enumerated(EnumType.STRING)
    private ClassFormat classFormat;
    private String auditorium;
    private String linkForTheClass;
    @ManyToOne
    private ProfessorIdTable professor;
    @OneToMany
    @JoinColumn(name = "attendance_id")
    private Set<AttendanceStatistics> attendanceStatistics;
}
