package sovcombank.jabka.studyservice.models;

import jakarta.persistence.*;
import lombok.Data;
import sovcombank.jabka.studyservice.models.enums.ClassFormat;

import java.util.Date;

@Entity
@Data
@Table(name = "schedule")
public class Schedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateTime;
    private StudyGroup group;
    @ManyToOne
    private Subject subject;
    @Enumerated(EnumType.STRING)
    private ClassFormat classFormat;
    private String auditorium;
    private String linkForTheClass;
    @ManyToOne
    private ProfessorSubject professor;
}
