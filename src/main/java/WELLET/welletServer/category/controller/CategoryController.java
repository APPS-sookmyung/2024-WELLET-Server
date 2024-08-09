package WELLET.welletServer.category.controller;

import WELLET.welletServer.category.domain.Category;
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
    public BasicResponse<CategoryUpdateDto> updateCategory(@PathVariable(name = "category_id") Long categoryId, @Valid @RequestBody CategoryUpdateDto dto) {
        Category category = categoryService.findById(categoryId);
        CategoryUpdateDto updatedCategory = categoryService.updateCategory(category, dto);
        return ResponseUtil.success(updatedCategory);
    }

    @DeleteMapping("/{category_id}")
    public BasicResponse<String> deleteCategory(@PathVariable(name = "category_id") Long categoryId) {
        Category category = categoryService.findById(categoryId);
        categoryService.deleteCategory(category);
        return ResponseUtil.success("그룹 삭제에 성공하였습니다. 그룹 id : " + categoryId);
    }

    @GetMapping("/{category_id}")
    public BasicResponse<List<CategoryCardListResponse>> findCardsByCategoryId(@PathVariable(name = "category_id") Category category) {
        List<CategoryCardListResponse> cardListResponses = categoryService.findCardsByCategoryId(category.getId());
        return ResponseUtil.success(cardListResponses);
    }

    @GetMapping
    public BasicResponse<List<CategoryCardListResponse>> findAllCards() {
        List<CategoryCardListResponse> cardListResponses = categoryService.findAllCards();
        return ResponseUtil.success(cardListResponses);
    }

    @GetMapping("/name")
    public BasicResponse<List<String>> findAllNames() {
        List<String> categoryNames = categoryService.findAllName();
        return ResponseUtil.success(categoryNames);
    }
}
