package WELLET.welletServer.category.controller;

import WELLET.welletServer.common.response.BasicResponse;
import WELLET.welletServer.common.response.ResponseUtil;
import WELLET.welletServer.category.dto.CategoryListName;
import WELLET.welletServer.category.dto.CategoryCardListResponse;
import WELLET.welletServer.category.dto.CategorySaveDto;
import WELLET.welletServer.category.dto.CategoryUpdateDto;
import WELLET.welletServer.category.service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @PostMapping
    public BasicResponse<String> create(@Valid @RequestBody CategorySaveDto categorySaveDto) {
        long CategoryId = categoryService.saveCategory(categorySaveDto);
        return ResponseUtil.success("그룹 생성에 성공하였습니다. 그룹 id : " + CategoryId);
    }

    @PutMapping("/{category_id}")
    public BasicResponse<CategoryUpdateDto> updateCategory(@PathVariable(name = "category_id") Long category_id, @Valid @RequestBody CategoryUpdateDto dto) {
        CategoryUpdateDto categoryUpdateDto = categoryService.updateCategory(category_id, dto);
        return ResponseUtil.success(categoryUpdateDto);
    }

    @DeleteMapping("/{category_id}")
    public BasicResponse<String> deleteCategory(@PathVariable(name = "category_id") Long category_id) {
        long categoryId = categoryService.deleteCategory(category_id);
        return ResponseUtil.success("그룹 삭제에 성공하였습니다. 그룹 id : " + categoryId);
    }

    @GetMapping("/{category_id}")
    public BasicResponse<List<CategoryCardListResponse>> findCardsByCategoryId(@PathVariable(name = "category_id") Long categoryId) {
        List<CategoryCardListResponse> cardListResponses = categoryService.findCardsByCategoryId(categoryId);
        return ResponseUtil.success(cardListResponses);
    }

    @GetMapping
    public BasicResponse<List<CategoryCardListResponse>> findAllCards() {
        List<CategoryCardListResponse> cardListResponses = categoryService.findAllCards();
        return ResponseUtil.success(cardListResponses);
    }

    @GetMapping("/name")
    public BasicResponse<List<CategoryListName>> findAllNames() {
        List<CategoryListName> categoryListNames = categoryService.findAllName();
        return ResponseUtil.success(categoryListNames);
    }
}
