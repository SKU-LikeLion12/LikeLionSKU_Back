package likelion.sku_sku.controller;

import likelion.sku_sku.service.SuggestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class SuggestionController {
    private final SuggestionService suggestionService;
}
