package sovcombank.jabka.studyservice.models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "study_groups")
public class StudyGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, nullable = false)
    private String name;
}
