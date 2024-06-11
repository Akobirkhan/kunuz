package kunuz.service;

import kunuz.dto.article.ArticleCreateDTO;
import kunuz.dto.article.ArticleResponseDTO;
import kunuz.entity.ArticleEntity;
import kunuz.entity.ProfileEntity;
import kunuz.enums.ArticleStatus;
import kunuz.exp.AppBadException;
import kunuz.repository.ArticleRepository;
import kunuz.repository.ArticleTypesRepository;
import kunuz.repository.CategoryRepository;
import kunuz.repository.RegionRepository;
import kunuz.util.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final ArticleTypesRepository articleTypesRepository;
    private final RegionRepository regionRepository;
    private final RegionService regionService;
    private final CategoryRepository categoryRepository;
    private final ArticleTypesService articleTypesService;

    @Autowired
    public ArticleService(ArticleRepository articleRepository, ArticleTypesRepository articleTypesRepository, RegionRepository regionRepository, RegionService regionService, CategoryRepository categoryRepository, ArticleTypesService articleTypesService) {
        this.articleRepository = articleRepository;
        this.articleTypesRepository = articleTypesRepository;
        this.regionRepository = regionRepository;
        this.regionService = regionService;
        this.categoryRepository = categoryRepository;
        this.articleTypesService = articleTypesService;
    }

    //    @Autowired
//    private ArticleTypeRepository articleTypeRepository;

    // 1 . CREATE (Moderator) status(NotPublished)
    public ArticleResponseDTO create(ArticleCreateDTO createDto) {
        ProfileEntity moderator = SecurityUtil.getProfile();

        ArticleEntity articleEntity = new ArticleEntity();
        articleEntity.setTitle(createDto.getTitle());
        articleEntity.setDescription(createDto.getDescription());
        articleEntity.setContent(createDto.getContent());
        articleEntity.setImageId(createDto.getImageId());
        articleEntity.setRegionId(createDto.getRegionId());
        articleEntity.setCategoryId(createDto.getCategoryId());
        articleEntity.setModeratorId((moderator.getId()));
        articleRepository.save(articleEntity);
        articleTypesService.create(articleEntity.getId(), createDto.getTypesList());

        return toDTO(articleEntity);
    }

    public ArticleResponseDTO update(String articleId, ArticleCreateDTO dto) {
        ArticleEntity entity = get(articleId);
        entity.setTitle(dto.getTitle());
        entity.setDescription(dto.getDescription());
        entity.setContent(dto.getContent());
        entity.setImageId(dto.getImageId());
        entity.setRegionId(dto.getRegionId());
        entity.setCategoryId(dto.getCategoryId());
        entity.setStatus(ArticleStatus.NOT_PUBLISHED);
        articleRepository.save(entity);

        articleTypesService.merge(articleId, dto.getTypesList());
        return toDTO(entity);
    }

    public ArticleResponseDTO toDTO(ArticleEntity entity) {
        ArticleResponseDTO dto = new ArticleResponseDTO();
        dto.setId(entity.getId());
        dto.setTitle(entity.getTitle());
        dto.setDescription(entity.getDescription());
        dto.setContent(entity.getContent());
        dto.setImageId(entity.getImageId());
        return dto;
    }

    public ArticleEntity get(String id) {
        return articleRepository.findById(id).orElseThrow(()
                -> new AppBadException("Article not found"));
    }

    public Boolean delete(String id) {
        ArticleEntity entity = get(id);
        entity.setVisible(Boolean.FALSE);
        articleRepository.save(entity);
        return true;
    }

    public Boolean changeStatus(String id) {
        ArticleEntity entity = get(id);
        if (entity.getStatus().equals(ArticleStatus.PUBLISHED))
            entity.setStatus(ArticleStatus.NOT_PUBLISHED);
        else
            entity.setStatus(ArticleStatus.PUBLISHED);
        articleRepository.save(entity);
        return true;
    }

    public List<ArticleResponseDTO> getTop5ArticleOrderedByCreatedDate() {
        List<ArticleEntity> entityList = articleRepository.findTopByCreatedDateAndStatusAndPublishedLimit5();
        List<ArticleResponseDTO> articleList = null;
        for (ArticleEntity entity : entityList) {
            articleList.add(toDTO(entity));
        }
        return articleList;
    }


}


