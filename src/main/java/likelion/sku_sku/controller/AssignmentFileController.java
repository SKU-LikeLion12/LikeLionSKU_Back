package likelion.sku_sku.controller;

import likelion.sku_sku.service.AssigmentFileService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AssignmentFileController {
    private final AssigmentFileService assigmentFileService;
}
