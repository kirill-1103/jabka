package sovcombank.jabka.studyservice.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;

@Entity
@Data
@Table(name = "subject")
public class Subject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    @ManyToMany
    @JoinTable(
            name = "group_subject",
            joinColumns = @JoinColumn(
                    name = "subject_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "study_group_name", referencedColumnName = "name"
            )
    )
    private Set<StudyGroup> studyGroup;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "subject" )
    private Set<StudyMaterials> studyMaterials;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "subject")
    private Set<Schedule> schedule;

    @Column(nullable = false, name="creator_id")
    private Long creatorId;

    @ManyToMany
    @JoinTable(
            name = "editor_subject",
            joinColumns = @JoinColumn(
                    name = "subject_id", referencedColumnName = "id"
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "editor_id", referencedColumnName = "professor_id"
            )
    )
    private Set<ProfessorIdTable> editorsIds;
}
