package WELLET.welletServer.category.service;

import WELLET.welletServer.category.dto.CategoryCardListResponse;
import WELLET.welletServer.category.domain.Category;
import WELLET.welletServer.categoryCard.domain.CategoryCard;
import WELLET.welletServer.category.dto.CategoryListName;
import WELLET.welletServer.category.dto.CategorySaveDto;
import WELLET.welletServer.category.dto.CategoryUpdateDto;
import WELLET.welletServer.category.exception.CategoryErrorCode;
import WELLET.welletServer.category.exception.CategoryException;
import WELLET.welletServer.category.reponsitory.CategoryRepository;
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

    public List<CategoryCardListResponse> findAllCards() {
        List<CategoryCard> categoryCards = categoryRepository.findAllCards();
        return categoryCards.stream()
                .map(CategoryCardListResponse::toCategoryList)
                .collect(Collectors.toList());
    }

    public List<CategoryCardListResponse> findCardsByCategoryId(Long categoryId) {
        List<CategoryCard> categoryCards = categoryRepository.findCardsByCategoryId(categoryId);
        return categoryCards.stream()
                .map(CategoryCardListResponse::toCategoryList)
                .collect(Collectors.toList());
    }

    public List<CategoryListName> findAllName() {
        List<Category> categories = categoryRepository.findAll();
        return categories.stream()
                .map(CategoryListName::fromCategory)
                .collect(Collectors.toList());
    }

    public List<Category> findCategoryNames(List<String> categoryNames) {
        return categoryNames.stream()
                .map(name -> categoryRepository.findByName(name)
                        .orElseThrow(() -> new CategoryException(CategoryErrorCode.CATEGORY_NOT_FOUND)))
                .collect(Collectors.toList());

    }
}
