package sovcombank.jabka.studyservice.models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "study_groups", uniqueConstraints = {
        @UniqueConstraint(columnNames = "groupName")
})
public class StudyGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
}
