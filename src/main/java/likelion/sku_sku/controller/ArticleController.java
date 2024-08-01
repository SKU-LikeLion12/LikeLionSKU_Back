package likelion.sku_sku.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import likelion.sku_sku.domain.Article;
import likelion.sku_sku.dto.ArticleDTO;
import likelion.sku_sku.service.ArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static likelion.sku_sku.dto.ArticleDTO.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/article")
@PreAuthorize("hasRole('ADMIN_LION')")
@Tag(name = "관리자 페이지: Article 관련")
public class ArticleController {
    private final ArticleService articleService;

    @PostMapping("/add")
    public Article saveArticle(@RequestBody ArticleCreateRequest request) {
        return  articleService.addArticle(
                request.getTrack(),
                request.getTitle(),
                request.getContent());
    }
}
