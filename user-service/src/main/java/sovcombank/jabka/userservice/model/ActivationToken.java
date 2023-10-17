package sovcombank.jabka.userservice.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Data
@Entity
@Table(name="activation_token")
public class ActivationToken {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String token;
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "user_id")
    private UserEntity userEntity;


    public ActivationToken(UserEntity userEntity) {
        this.userEntity = userEntity;
        createdDate = new Date();
        token = UUID.randomUUID().toString();
    }
}
