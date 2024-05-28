package kunuz.dto.auth;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RegistrationByPhoneDTO {
    @NotBlank
    @NotBlank
    private String name;
    @NotBlank
    private String surname;
    @NotBlank
    private String phone;
    @NotBlank
    private String password;
}
