package WELLET.welletServer.category.service;

import WELLET.welletServer.card.domain.Card;
import WELLET.welletServer.category.dto.*;
import WELLET.welletServer.category.domain.Category;
import WELLET.welletServer.category.exception.CategoryErrorCode;
import WELLET.welletServer.category.exception.CategoryException;
import WELLET.welletServer.category.reponsitory.CategoryRepository;
import WELLET.welletServer.member.domain.Member;
import WELLET.welletServer.member.exception.MemberErrorCode;
import WELLET.welletServer.member.exception.MemberException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Stream;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CategoryService {
    private final CategoryRepository categoryRepository;

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
    public CategoryUpdateDto updateCategory(Member member, Long categoryId, CategoryUpdateDto dto) {
        Category category = findById(categoryId);
        authenticateUser(member, category);

        categoryRepository.findByMemberAndName(member, dto.getName())
                .ifPresent(e -> {
                    throw new CategoryException(CategoryErrorCode.CATEGORY_DUPLICATE);
                });

        category.updateCategory(dto);
        return CategoryUpdateDto.toCategoryUpdateDto(category);
    }

    @Transactional
    public void deleteCategory(Member member, Category category, List<Card> cardList) {
        authenticateUser(member, category);

        if (cardList != null && !cardList.isEmpty()) {
            cardList.forEach(Card::updateCategoryWithNull);
        }

        categoryRepository.delete(category);
    }

    public List<CategoryListName> findAllName(Member member) {
        List<Category> categories = categoryRepository.findByMember(member);

        return categories.stream()
                .map(CategoryListName::toCategoryList)
                .toList();
    }

    public Category findById(Long categoryId) {
        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> new CategoryException(CategoryErrorCode.CATEGORY_NOT_FOUND));
    }

    public Category findByName(Member member, String name){
        return categoryRepository.findByMemberAndName(member, name)
                .orElseThrow(() -> new CategoryException(CategoryErrorCode.CATEGORY_NOT_FOUND));
    }

    private static void authenticateUser(Member member, Category category) {
        if (!category.getMember().equals(member) ) throw new MemberException(MemberErrorCode.UNAUTHORIZED_USER);
    }
}
