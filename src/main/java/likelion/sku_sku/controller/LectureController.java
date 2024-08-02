package likelion.sku_sku.controller;

import likelion.sku_sku.service.LectureService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class LectureController {
    private final LectureService lectureService;
}
