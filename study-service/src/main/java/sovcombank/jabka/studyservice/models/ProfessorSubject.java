package sovcombank.jabka.studyservice.models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "professor_subject")
public class ProfessorSubject {
    @Id
    private Long professorId;
    @Id
    private Long subjectId;
}
