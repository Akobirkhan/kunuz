package kunuz.dto.article;


import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Setter
@Getter
public class ArticleResponseDTO {
    private String id;
    private String title;
    private String description;
    private String content;
    private Integer sharedCount;
    private Integer viewCount;
    private Integer imageId;
    private LocalDateTime createdDate;
    private LocalDateTime publishedDate;
    private Boolean visible;
}
