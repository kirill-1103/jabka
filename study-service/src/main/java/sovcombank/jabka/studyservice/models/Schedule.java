package sovcombank.jabka.studyservice.models;

import jakarta.persistence.*;
import lombok.Data;
import sovcombank.jabka.studyservice.models.enums.ClassFormat;

import java.util.Date;
import java.util.List;

@Entity
@Data
@Table(name = "schedule")
public class Schedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ElementCollection
    private List<String> groupNumber;
    @OneToMany
    private List<Subject> subject;
    @Temporal(TemporalType.DATE)
    private Date date;
    @Temporal(TemporalType.TIME)
    private Date time;
    @Enumerated(EnumType.STRING)
    private ClassFormat classFormat;
    private String auditorium;
    private String linkForTheClass;
}
