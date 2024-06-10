package kunuz.dto.profile;

import com.fasterxml.jackson.annotation.JsonInclude;
import kunuz.enums.ProfileRole;
import kunuz.enums.ProfileStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProfileCreateDTO {
    private String name;
    private String surname;
    private String email;
    private String phone;
    private String password;
    private ProfileStatus status;
    private ProfileRole role;
}
