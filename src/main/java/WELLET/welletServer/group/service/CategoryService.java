package WELLET.welletServer.group.service;

import WELLET.welletServer.group.domain.Category;
import WELLET.welletServer.group.domain.CategoryCard;
import WELLET.welletServer.group.dto.CategoryListResponse;
import WELLET.welletServer.group.dto.CategorySaveDto;
import WELLET.welletServer.group.dto.CategoryUpdateDto;
import WELLET.welletServer.group.exception.CategoryErrorCode;
import WELLET.welletServer.group.exception.CategoryException;
import WELLET.welletServer.group.reponsitory.CategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CategoryService {
    private final CategoryRepository categoryRepository;

    @Transactional
    public long saveCategory (CategorySaveDto dto) {
        Category category = Category.builder()
                .name(dto.getName())
                .build();
        return categoryRepository.save(category).getId();
    }

    @Transactional
    public CategoryUpdateDto updateCategory(Long categoryId, CategoryUpdateDto dto) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new CategoryException(CategoryErrorCode.CATEGORY_NOT_FOUND));

        category.updateCategory(dto);
        return CategoryUpdateDto.toCategoryUpdateDto(category);
    }

    @Transactional
    public long deleteCategory(Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new CategoryException(CategoryErrorCode.CATEGORY_NOT_FOUND));
        categoryRepository.delete(category);
        return categoryId;
    }

    public List<CategoryListResponse> findCategoryList(Long groupId) {
        List<CategoryCard> categoryCards;
        if(groupId == null) {
            categoryCards = categoryRepository.findAllCards();
        } else {
            categoryCards = categoryRepository.findCardsByCategoryId(groupId);
        }
        return categoryCards.stream()
                .map(CategoryListResponse::toCategoryList)
                .collect(Collectors.toList());
    }
}
