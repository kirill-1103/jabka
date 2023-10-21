package sovcombank.jabka.studyservice.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "professor_id")
@AllArgsConstructor
@NoArgsConstructor
public class ProfessorIdTable {
    @Id
    private Long professorId;
}
