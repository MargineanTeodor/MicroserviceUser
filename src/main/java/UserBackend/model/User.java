package UserBackend.model;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotNull
    @Size(min=1,max=30)
    private String username;
    @NotNull
    @Size(min=1,max=30)
    private String password;
    @NotNull
    @Size(min=1,max=30)
    private String name;
    @NotNull
    private String role;
}
