package WELLET.welletServer.category.controller;

import WELLET.welletServer.category.domain.Category;
import WELLET.welletServer.common.response.BasicResponse;
import WELLET.welletServer.common.response.ResponseUtil;
import WELLET.welletServer.category.dto.CategoryListName;
import WELLET.welletServer.category.dto.CategoryCardListResponse;
import WELLET.welletServer.category.dto.CategorySaveDto;
import WELLET.welletServer.category.dto.CategoryUpdateDto;
import WELLET.welletServer.category.service.CategoryService;
import WELLET.welletServer.member.domain.Member;
import WELLET.welletServer.member.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories/{member_id}")
@RequiredArgsConstructor
@Tag(name = "그룹", description = "Category API")
public class CategoryController {
    private final CategoryService categoryService;
    private final MemberService memberService;

    @PostMapping
    @Operation(summary = "그룹 생성")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "그룹 생성에 성공하였습니다."),
            @ApiResponse(responseCode = "400", description = "회원을 찾을 수 없습니다."),
            @ApiResponse(responseCode = "400", description = "이미 존재하는 그룹입니다."),
    })
    @Parameters({
            @Parameter(name = "member_id", description = "공백 X", example = "1"),
    })
    public BasicResponse<String> create(@PathVariable Long member_id, @Valid @RequestBody CategorySaveDto categorySaveDto) {
        Member member = memberService.findMember(member_id);
        long CategoryId = categoryService.saveCategory(member, categorySaveDto);
        return ResponseUtil.success("그룹 생성에 성공하였습니다. 그룹 id : " + CategoryId);
    }

    @PutMapping("/{category_id}")
    @Operation(summary = "그룹 수정")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "그룹 수정에 성공하였습니다"),
            @ApiResponse(responseCode = "400", description = "회원을 찾을 수 없습니다."),
            @ApiResponse(responseCode = "400", description = "그룹이 존재하지 않습니다."),
    })
    @Parameters({
            @Parameter(name = "member_id", description = "공백 X", example = "1"),
            @Parameter(name = "category_id", description = "공백 X", example = "1"),
    })
    public BasicResponse<CategoryUpdateDto> updateCategory(@PathVariable Long member_id, @PathVariable(name = "category_id") Long categoryId, @Valid @RequestBody CategoryUpdateDto dto) {
        memberService.findMember(member_id);
        Category category = categoryService.findById(categoryId);
        CategoryUpdateDto updatedCategory = categoryService.updateCategory(category, dto);
        return ResponseUtil.success(updatedCategory);
    }

    @DeleteMapping("/{category_id}")
    @Operation(summary = "그룹 삭제")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "그룹 삭제에 성공하였습니다"),
            @ApiResponse(responseCode = "400", description = "회원을 찾을 수 없습니다."),
            @ApiResponse(responseCode = "400", description = "그룹이 존재하지 않습니다."),
    })
    @Parameters({
            @Parameter(name = "member_id", description = "공백 X", example = "1"),
            @Parameter(name = "category_id", description = "공백 X", example = "1"),
    })
    public BasicResponse<String> deleteCategory(@PathVariable Long member_id, @PathVariable(name = "category_id") Long categoryId) {
        memberService.findMember(member_id);
        Category category = categoryService.findById(categoryId);
        categoryService.deleteCategory(category);
        return ResponseUtil.success("그룹 삭제에 성공하였습니다. 그룹 id : " + categoryId);
    }

    @GetMapping("/{category_id}")
    @Operation(summary = "그룹별 명함 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "명함 조회에 성공하였습니다"),
            @ApiResponse(responseCode = "400", description = "회원을 찾을 수 없습니다."),
            @ApiResponse(responseCode = "400", description = "존재하지 않은 그룹입니다"),
    })
    @Parameters({
            @Parameter(name = "member_id", description = "공백 X", example = "1"),
            @Parameter(name = "category_id", description = "공백 X", example = "1"),
    })
    public BasicResponse<List<CategoryCardListResponse>> findCardsByCategoryId(@PathVariable Long member_id, @PathVariable(name = "category_id") Long categoryId) {
        memberService.findMember(member_id);
        List<CategoryCardListResponse> cardListResponses = categoryService.findCardsByCategoryId(member_id, categoryId);
        return ResponseUtil.success(cardListResponses);
    }

    @GetMapping
    @Operation(summary = "전체 그룹별 명함 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "전체 명함 조회에 성공하였습니다"),
            @ApiResponse(responseCode = "400", description = "회원을 찾을 수 없습니다."),
    })
    @Parameters({
            @Parameter(name = "member_id", description = "공백 X", example = "1"),
            @Parameter(name = "category_id", description = "공백 X", example = "1"),
    })
    public BasicResponse<List<CategoryCardListResponse>> findAllCards(@PathVariable Long member_id) {
        List<CategoryCardListResponse> cardListResponses = categoryService.findAllCards(member_id);
        return ResponseUtil.success(cardListResponses);
    }

    @GetMapping("/name")
    @Operation(summary = "그룹명 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "그룹명 조회에 성공하였습니다"),
            @ApiResponse(responseCode = "400", description = "회원을 찾을 수 없습니다."),
            @ApiResponse(responseCode = "400", description = "존재하지 않은 그룹입니다"),
    })
    @Parameters({
            @Parameter(name = "member_id", description = "공백 X", example = "1"),
            @Parameter(name = "category_id", description = "공백 X", example = "1"),
    })
    public BasicResponse<List<String>> findAllNames(@PathVariable Long member_id) {
        Member member = memberService.findMember(member_id);
        List<String> categoryNames = categoryService.findAllName(member);
        return ResponseUtil.success(categoryNames);
    }
}
