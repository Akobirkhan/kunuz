package kunuz.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CategoryByLangDTO {
    private Integer id;
    private Integer orderNumber;
    private String name;
}
