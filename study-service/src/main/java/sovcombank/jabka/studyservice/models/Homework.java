package sovcombank.jabka.studyservice.models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "homework")
public class Homework {
    @Id
    @GeneratedValue
    private Long id;
    @ManyToOne
    @JoinColumn(name = "study_materials_id")
    private StudyMaterials task;
}
