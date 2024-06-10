package kunuz.dto.articleLike;

import kunuz.enums.ArticleLikeStatus;

import java.time.LocalDateTime;

public class ArticleLikeDTO {
    private Integer id;
    private Integer profile_id;
    private Integer article_id;
    private LocalDateTime created_date;
    private ArticleLikeStatus status;
}
