package kunuz.dto.articleLike;

import kunuz.enums.ArticleLikeStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter
@Setter
public class ArticleLikeDTO {
    private Integer id;
    private Integer profileId;
    private String articleId;
    private LocalDateTime created_date;
    private ArticleLikeStatus status;
}
