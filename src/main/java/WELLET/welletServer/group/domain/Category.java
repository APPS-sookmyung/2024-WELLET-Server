package WELLET.welletServer.group.domain;

import WELLET.welletServer.group.dto.CategoryUpdateDto;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table (name = "Category")
@NoArgsConstructor (access = AccessLevel.PROTECTED)
public class Category {
    @Id
    @Column(name = "category_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(name = "name", nullable = false)
    private String name;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CategoryCard> categoryCards = new ArrayList<>();

    @Builder
    public Category(String name) {
        this.name = name;
    }

    public void addCategoryCard(CategoryCard categoryCard) {
        categoryCards.add(categoryCard);
    }

    public void updateCategory(CategoryUpdateDto dto) {
        this.name = dto.getName();
    }
}