package kunuz.repository;

import kunuz.dto.article.ArticleResponseDTO;
import kunuz.entity.ArticleEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ArticleRepository extends CrudRepository<ArticleEntity, String> {
    @Query("from ArticleEntity where status='PUBLISHED' order by createdDate desc limit 5 "  )
    List<ArticleEntity> findTopByCreatedDateAndStatusAndPublishedLimit5();
}
