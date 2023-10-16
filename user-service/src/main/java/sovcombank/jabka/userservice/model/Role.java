package sovcombank.jabka.userservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.sovcombank.openapi.model.ERoleOpenApi;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Role {
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(unique = true)
    private ERoleOpenApi name;

    public Role(ERoleOpenApi name) {
        this.name = name;
    }
}
