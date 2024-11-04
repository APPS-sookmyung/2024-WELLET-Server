package WELLET.welletServer.category.domain;

import WELLET.welletServer.card.domain.Card;
import WELLET.welletServer.category.dto.CategoryUpdateDto;
import WELLET.welletServer.member.domain.Member;
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
    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Builder
    public Category(Member member, String name) {
        this.member = member;
        this.name = name;
    }
    public void updateCategory(CategoryUpdateDto dto) {
        this.name = dto.getName();
    }
}
