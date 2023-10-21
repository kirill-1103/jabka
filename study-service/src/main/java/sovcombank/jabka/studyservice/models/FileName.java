package sovcombank.jabka.studyservice.models;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
@Entity
@Table(name = "files")
@Data
@Builder
public class FileName {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name="initial_name")
    private String initialName;
    @Column(name="name_s3",unique = true)
    private String nameS3;
    @ManyToOne
    private Homework homework;
}
