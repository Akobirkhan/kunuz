package kunuz.dto.auth;

import jakarta.persistence.criteria.CriteriaBuilder;
import kunuz.enums.ProfileRole;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JwtDTO {
    private Integer id;
    private String username;
    private ProfileRole role;

    public JwtDTO(Integer id, String userName, ProfileRole role) {
        this.id = id;
        this.role = role;
        this.username = userName;
    }

    public JwtDTO(Integer id) {
        this.id = id;
    }
}
