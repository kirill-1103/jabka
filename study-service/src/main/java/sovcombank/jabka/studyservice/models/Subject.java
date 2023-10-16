package sovcombank.jabka.studyservice.models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "subject")
public class Subject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @ManyToMany
    @JoinTable(
            name="group_subject",
            joinColumns = @JoinColumn(
                    name="subject_name", referencedColumnName = "name"),
            inverseJoinColumns = @JoinColumn(
                    name="study_groups_name", referencedColumnName = "name"
            )
    )
    private StudyGroup studyGroup;
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private StudyMaterials studyMaterials;
    @OneToMany
    private Schedule schedule;
}
