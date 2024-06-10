package kunuz.dto.article;

import jakarta.validation.constraints.*;
import kunuz.enums.ProfileRole;
import lombok.Getter;
import lombok.Setter;

import java.lang.reflect.Array;
import java.util.List;

@Setter
@Getter
public class ArticleCreateDTO {

    @NotBlank(message = "Title cannot be empty")
    @Size(min = 3, message = "The length of the title cannot be less than 3 letters")
    private String title;

    @NotBlank(message = "Description cannot be empty")
    @Size(min = 3, message = "The length of the description cannot be less than 3 letters")
    private String description;

    @NotBlank(message = "Content cannot be empty")
    @Size(min = 3, message = "The length of the content cannot be less than 3 letters")
    private String content;

    private Integer imageId;

    @NotNull(message = "regionId cannot be empty")
//    @Size(min = 1, message = "The value of regionId should be at least 1")
    @Positive(message = "id cannot ")
    private Integer regionId;

    @NotNull(message = "categoryId cannot be empty")
//    @Size(min = 1, message = "The value of categoryId should be at least 1")
    private Integer categoryId;

    @NotNull(message = "typesList cannot be empty")
    @NotEmpty(message = "")
    private List<Integer> typesList;
}
