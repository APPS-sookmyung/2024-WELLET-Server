package WELLET.welletServer.group.controller;

import WELLET.welletServer.common.response.BasicResponse;
import WELLET.welletServer.common.response.ResponseUtil;
import WELLET.welletServer.group.dto.CategorySaveDto;
import WELLET.welletServer.group.service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/groups")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @PostMapping
    public BasicResponse<String> create(@Valid @RequestBody CategorySaveDto categorySaveDto) {
        long CategoryId = categoryService.saveCategory(categorySaveDto);
        return ResponseUtil.success("그룹 생성에 성공하였습니다. 그룹 id : " + CategoryId);
    }
}
