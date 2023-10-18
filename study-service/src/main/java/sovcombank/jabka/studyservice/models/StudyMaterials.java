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
    @JoinColumn(name = "subject_id")
    private Subject subject;
    private String materialsText;
    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "homework_id")
    private Set<Homework> homeworks;
    private Set<String> attachedFiles;
    @OneToMany
    @JoinColumn(name = "files_id")
    private Set<File> files;
}
