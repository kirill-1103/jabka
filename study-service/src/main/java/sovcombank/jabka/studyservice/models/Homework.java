package sovcombank.jabka.studyservice.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NonNull;

import java.util.Date;
import java.util.Set;

@Data
@Entity
@Table(name = "homework")
public class Homework {
    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    @NonNull
    private Long studentId;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

    private Long grade;

    private String comment;

    @OneToMany
    @JoinColumn(name = "homework_id")
    private Set<FileName> fileNames;

    @ManyToOne
    @JoinColumn(name = "study_materials_id")
    private StudyMaterials task;
}
