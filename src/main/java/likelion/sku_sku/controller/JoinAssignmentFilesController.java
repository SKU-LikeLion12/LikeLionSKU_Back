package likelion.sku_sku.controller;

import likelion.sku_sku.service.JoinAssignmentFilesService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class JoinAssignmentFilesController {
    private final JoinAssignmentFilesService assigmentFileService;
}
