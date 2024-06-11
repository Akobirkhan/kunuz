package kunuz.controller;

import jakarta.validation.Valid;
import kunuz.dto.article.ArticleCreateDTO;
import kunuz.dto.article.ArticleResponseDTO;
import kunuz.service.ArticleService;
import kunuz.service.ArticleTypesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/article")
@RestController
public class ArticleController {
    @Autowired
    private ArticleService articleService;

    // 1. CREATE (Moderator) status(NotPublished)
    @PreAuthorize("hasRole('MODERATOR')")
    @PostMapping("/moderator")
    public ResponseEntity<ArticleResponseDTO> create(@Valid @RequestBody ArticleCreateDTO createDto) {
        return ResponseEntity.ok(articleService.create(createDto));
    }

    // 2. Update (Moderator (status to not publish)) (remove old image)
    @PreAuthorize("hasAnyRole('MODERATOR', 'PUBLISHER')")
    @PutMapping("/moderator/{id}")
    public ResponseEntity<ArticleResponseDTO> update(@Valid @RequestBody ArticleCreateDTO createDto,
                                                     @PathVariable("id ") String id) {
        return ResponseEntity.ok(articleService.update(id, createDto));
    }
    @PreAuthorize("hasRole('MODERATOR')")
    @DeleteMapping("/moderator/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable("id") String id){
        return ResponseEntity.ok(articleService.delete(id));
    }

    @PreAuthorize("hasRole('PUBLISHER')")
    @PutMapping("/publisher/{id}")
    public ResponseEntity<Boolean> changeStatus(@PathVariable("id") String id){
        return ResponseEntity.ok(articleService.changeStatus(id));
    }

    @GetMapping("/getTop5")
    public ResponseEntity<List<ArticleResponseDTO>> getArticleTop5(){
        return ResponseEntity.ok(articleService.getTop5ArticleOrderedByCreatedDate());
    }



}
