package sovcombank.jabka.studyservice.models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "subjects")
public class Subject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String disciplineName;
    private String professorName;
    @ManyToOne
    private Schedule schedule;
}
