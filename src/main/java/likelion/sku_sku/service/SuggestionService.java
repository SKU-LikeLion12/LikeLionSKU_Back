package likelion.sku_sku.service;

import likelion.sku_sku.domain.Suggestion;
import likelion.sku_sku.domain.enums.TrackType;
import likelion.sku_sku.exception.InvalidIdException;
import likelion.sku_sku.repository.SuggestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SuggestionService {

    private final SuggestionRepository suggestionRepository;
    @Transactional
    public Suggestion addArticle(TrackType track, String title, String content) {
        Suggestion article = new Suggestion(track, title, content);
        return suggestionRepository.save(article);
    }
    @Transactional
    public Suggestion updateArticle(Long id, TrackType track, String title, String content) {
        Suggestion article = suggestionRepository.findById(id)
                .orElseThrow(InvalidIdException::new);
        TrackType newTrack = (track != null ? track : article.getTrack());
        String newTitle = (title != null && !title.isEmpty() ? title : article.getTitle());
        String newContent = (content != null && !content.isEmpty() ? content : article.getContent());
        article.update(newTrack, newTitle, newContent);
        return suggestionRepository.save(article);
    }

    public List<Suggestion> findAllArticle() {
        return suggestionRepository.findAll();
    }

    public Suggestion findArticleById(Long id) {
        return suggestionRepository.findById(id)
                .orElseThrow(InvalidIdException::new);
    }

}
