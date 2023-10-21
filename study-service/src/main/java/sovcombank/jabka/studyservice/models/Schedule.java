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
    @Column(name="date_time")
    private Date dateTime;
    @ManyToOne
    private StudyGroup group;
    @ManyToOne
    private Subject subject;
    @Enumerated(EnumType.STRING)
    @Column(name="class_format")
    private ClassFormat classFormat;
    private String auditorium;
    @Column(name="link_for_the_class")
    private String linkForTheClass;
    @ManyToOne
    private ProfessorIdTable professor;
    @OneToMany(mappedBy = "schedule")
    private Set<AttendanceStatistics> attendanceStatistics;
}
