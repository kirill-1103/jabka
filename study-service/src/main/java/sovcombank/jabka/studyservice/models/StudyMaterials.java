package sovcombank.jabka.studyservice.models;

import jakarta.persistence.*;
import lombok.Data;
import sovcombank.jabka.studyservice.models.enums.StudyMaterialsType;

import java.util.Set;

@Data
@Entity
@Table(name = "study_materials")
public class StudyMaterials {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private StudyMaterialsType type;
    @ManyToOne
    private Subject subject;
    private String materialsText;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "task")
    private Set<Homework> homeworks;

    @ManyToMany
    @JoinTable(
            name = "materials_files",
            joinColumns = @JoinColumn(
                    name = "materials_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "file_id", referencedColumnName = "id"
            )
    )
    private Set<FileName> attachedFiles;
}
