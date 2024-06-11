package kunuz.service;

import kunuz.dto.articleLike.ArticleLikeDTO;
import kunuz.entity.ArticleLikeEntity;
import kunuz.enums.ArticleLikeStatus;
import kunuz.exp.AppBadException;
import kunuz.repository.ArticleLikeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class ArticleLikeService {
    @Autowired
    private ArticleLikeRepository articleLikeRepository;

    public Boolean like(String articleId, Integer profileId){
        makeEmotion(articleId, profileId, ArticleLikeStatus.LIKE);
        return true;
    }
    public Boolean dislike(String articleId, Integer profileId){
        makeEmotion(articleId, profileId, ArticleLikeStatus.DISLIKE);
        return true;
    }

    public Boolean delete(String articleId, Integer profileId){
        articleLikeRepository.delete(articleId, profileId);
        return true;
    }


    public void makeEmotion(String articleId, Integer profileId, ArticleLikeStatus status){
        Optional<ArticleLikeEntity> optional = articleLikeRepository
                .findByArticleIdAndProfileId(articleId, profileId);
        if (optional.isEmpty()){
            ArticleLikeEntity entity = new ArticleLikeEntity();
            entity.setArticleId(articleId);
            entity.setProfileId(profileId);
            entity.setStatus(status);
            entity.setCreatedDate(LocalDateTime.now());
            articleLikeRepository.save(entity);
        }
        else
            articleLikeRepository.update(status, articleId, profileId);
    }
}
