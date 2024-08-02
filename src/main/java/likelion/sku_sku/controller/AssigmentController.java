package likelion.sku_sku.controller;

import likelion.sku_sku.service.AssignmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AssigmentController {
    private final AssignmentService assignmentService;
}
