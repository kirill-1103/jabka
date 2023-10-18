package sovcombank.jabka.studyservice.models;

import jakarta.persistence.*;
import lombok.Data;
@Entity
@Table(name = "files")
@Data
public class File {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String initialName;
    @Column(unique = true)
    private String nameS3;

}
