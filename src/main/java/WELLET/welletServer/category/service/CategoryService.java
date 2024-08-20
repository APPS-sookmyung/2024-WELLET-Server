package WELLET.welletServer.category.service;

import WELLET.welletServer.category.dto.CategoryCardListResponse;
import WELLET.welletServer.category.domain.Category;
import WELLET.welletServer.category.dto.CategoryCountResponse;
import WELLET.welletServer.categoryCard.domain.CategoryCard;
import WELLET.welletServer.category.dto.CategorySaveDto;
import WELLET.welletServer.category.dto.CategoryUpdateDto;
import WELLET.welletServer.category.exception.CategoryErrorCode;
import WELLET.welletServer.category.exception.CategoryException;
import WELLET.welletServer.category.reponsitory.CategoryRepository;
import WELLET.welletServer.categoryCard.repository.CategoryCardRepository;
import WELLET.welletServer.member.domain.Member;
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
    private final CategoryCardRepository categoryCardRepository;

    @Transactional
    public long saveCategory (Member member, CategorySaveDto dto) {
        categoryRepository.findByMemberAndName(member, dto.getName()).ifPresent(e -> {
            throw new CategoryException(CategoryErrorCode.CATEGORY_DUPLICATE);
        });

        Category category = Category.builder()
                .member(member)
                .name(dto.getName())
                .build();
        return categoryRepository.save(category).getId();
    }

    @Transactional
    public CategoryUpdateDto updateCategory(Category category, CategoryUpdateDto dto) {
        category.updateCategory(dto);
        return CategoryUpdateDto.toCategoryUpdateDto(category);
    }

    @Transactional
    public void deleteCategory(Category category) {
        categoryCardRepository.deleteByCategory(category);
        categoryRepository.delete(category);
    }

    public CategoryCountResponse findCardsByIds(Long memberId, Long categoryId) {
        categoryRepository.findById(categoryId)
                .orElseThrow(() -> new CategoryException(CategoryErrorCode.CATEGORY_NOT_FOUND));

        List<CategoryCardListResponse> responses = categoryRepository.findCardsByIds(memberId, categoryId).stream()
                .map(CategoryCardListResponse::toCategoryList)
                .toList();

        return new CategoryCountResponse(responses.size(), responses);
    }

    public List<String> findAllName(Member member) {
        List<Category> categories = categoryRepository.findByMember(member);
        return categories.stream()
                .map(Category::getName)
                .collect(Collectors.toList());
    }

    public List<Category> findCategoryNames(Member member, List<String> categoryNames) {
        return categoryNames.stream()
                .map(name -> categoryRepository.findByMemberAndName(member, name)
                        .orElseThrow(() -> new CategoryException(CategoryErrorCode.CATEGORY_NOT_FOUND)))
                .collect(Collectors.toList());
    }

    public Category findById(Long categoryId) {
        if (categoryId == null || categoryId <= 0) {
            throw new CategoryException(CategoryErrorCode.CATEGORY_NOT_FOUND);
        }
        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> new CategoryException(CategoryErrorCode.CATEGORY_NOT_FOUND));
    }
}
