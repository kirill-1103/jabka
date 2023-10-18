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
    @Column(unique = true)
    private String initialName;
    @Column(unique = true)
    private String nameS3;
}
