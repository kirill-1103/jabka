package sovcombank.jabka.studyservice.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.Date;
import java.util.Set;

@Data
@Entity
@Table(name = "homework")
@NoArgsConstructor
public class Homework {
    @Id
    @GeneratedValue
    private Long id;

    @Column(name="student_id",nullable = false)
    @NonNull
    private Long studentId;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

    private Long grade;

    private String comment;

    @OneToMany(mappedBy = "homework")
    private Set<FileName> fileNames;

    @ManyToOne
    private StudyMaterials task;
}
