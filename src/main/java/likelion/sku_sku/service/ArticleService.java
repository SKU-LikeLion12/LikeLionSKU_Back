package likelion.sku_sku.service;

import likelion.sku_sku.domain.Article;
import likelion.sku_sku.domain.RoleType;
import likelion.sku_sku.domain.TrackType;
import likelion.sku_sku.dto.ProjectDTO;
import likelion.sku_sku.exception.InvalidArticleIdException;
import likelion.sku_sku.exception.InvalidProjectIdException;
import likelion.sku_sku.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ArticleService {

    private final ArticleRepository articleRepository;
    @Transactional
    public Article addArticle(TrackType track, String title, String content) {
        Article article = new Article(track, title, content);
        return articleRepository.save(article);
    }
    @Transactional
    public Article updateArticle(Long id, TrackType track, String title, String content) {
        Article article = articleRepository.findById(id)
                .orElseThrow(InvalidArticleIdException::new);
        TrackType newTrack = (track != null ? track : article.getTrack());
        String newTitle = (title != null && !title.isEmpty() ? title : article.getTitle());
        String newContent = (content != null && !content.isEmpty() ? content : article.getContent());
        article.update(newTrack, newTitle, newContent);
        return articleRepository.save(article);
    }

    public List<Article> findAllArticle() {
        return articleRepository.findAll();
    }

    public Article findArticleById(Long id) {
        return articleRepository.findById(id)
                .orElseThrow(InvalidArticleIdException::new);
    }

}
