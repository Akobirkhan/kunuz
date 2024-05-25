package kunuz.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SmsHistoryDTO {
    private Integer id;
    private String message;
    private String phone;
    private LocalDateTime createdDate;
}
