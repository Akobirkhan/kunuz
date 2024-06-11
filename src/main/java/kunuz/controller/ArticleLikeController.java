package kunuz.controller;

import jakarta.servlet.http.HttpServletRequest;

import kunuz.dto.auth.JwtDTO;
import kunuz.service.ArticleLikeService;
import kunuz.util.JWTUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/article_like")
@RestController
public class ArticleLikeController {
    @Autowired
    private ArticleLikeService articleLikeService;
    @PreAuthorize("permitAll()")
    @PostMapping("/like/{id}")
    public ResponseEntity<Boolean> like(@PathVariable("id") String articleId,
                                        @RequestHeader("Authorization") String authorization) {
        JwtDTO jwt = JWTUtil.getJwtDTO(authorization);
        return ResponseEntity.ok(articleLikeService.like(articleId, jwt.getId()));
    }
    @PreAuthorize("permitAll()")
    @PostMapping("/dislike/{id}")
    public ResponseEntity<Boolean> dislike(@PathVariable("id") String articleId,
                                           @RequestHeader("Authorization") String authorization) {
        JwtDTO jwt = JWTUtil.getJwtDTO(authorization);
        return ResponseEntity.ok(articleLikeService.dislike(articleId, jwt.getId()));
    }
    @PreAuthorize("permitAll()")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable("id") String articleId,
                                          @RequestHeader("Authorization") String authorization) {
        JwtDTO jwt = JWTUtil.getJwtDTO(authorization);
        return ResponseEntity.ok(articleLikeService.delete(articleId, jwt.getId()));
    }
}
