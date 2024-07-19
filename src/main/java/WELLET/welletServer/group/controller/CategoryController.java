package WELLET.welletServer.group.controller;

import WELLET.welletServer.common.response.BasicResponse;
import WELLET.welletServer.common.response.ResponseUtil;
import WELLET.welletServer.group.dto.CategorySaveDto;
import WELLET.welletServer.group.dto.CategoryUpdateDto;
import WELLET.welletServer.group.service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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

    @PutMapping("/{group_id}")
    public BasicResponse<CategoryUpdateDto> updateCategory(@PathVariable(name = "group_id") Long group_id, @Valid @RequestBody CategoryUpdateDto dto) {
        CategoryUpdateDto categoryUpdateDto = categoryService.updateCategory(group_id, dto);
        return ResponseUtil.success(categoryUpdateDto);
    }

    @DeleteMapping("/{group_id}")
    public BasicResponse<String> deleteCategory(@PathVariable(name = "group_id") Long group_id) {
        long categoryId = categoryService.deleteCategory(group_id);
        return ResponseUtil.success("그룹 삭제에 성공하였습니다. 그룹 id : " + categoryId);
    }
}
