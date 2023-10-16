package sovcombank.jabka.studyservice.models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "professor_subject")
public class ProfessorSubject {
    private Long professorId;
    private Long subjectId;
}