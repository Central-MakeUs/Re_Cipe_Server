package com.re_cipe.search.ui

import com.re_cipe.global.annotation.CurrentMember
import com.re_cipe.global.response.ApiResponse
import com.re_cipe.member.domain.Member
import com.re_cipe.recipe.ui.dto.RecipeResponse
import com.re_cipe.recipe.ui.dto.ShortFormSimpleResponse
import com.re_cipe.search.service.SearchService
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import org.springframework.data.domain.Slice
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/api/v1/search")
@RestController
@Api(tags = ["search api"], description = "Re:cipe API")
class SearchController(
    private val searchService: SearchService
) {
    @ApiOperation(value = "인기 검색어를 조회한다.")
    @SecurityRequirement(name = "Authorization")
    @GetMapping("/keywords")
    fun getPopularKeyword(
        @CurrentMember member: Member,
    ): ApiResponse<List<String>> {
        return ApiResponse.success(searchService.findPopularKeywords())
    }

    @ApiOperation(value = "레시피을 키워드로 검색한다.")
    @SecurityRequirement(name = "Authorization")
    @GetMapping("/recipe")
    fun findRecipeByKeyword(
        @CurrentMember member: Member,
        @RequestParam keyword: String,
        @RequestParam(required = false, defaultValue = "0") offset: Int,
        @RequestParam(required = false, defaultValue = "20") pageSize: Int,
    ): ApiResponse<Slice<RecipeResponse>> {
        return ApiResponse.success(
            searchService.findRecipeByKeyword(
                keyword = keyword,
                offset = offset,
                pageSize = pageSize,
                member = member
            )
        )
    }

    @ApiOperation(value = "숏폼을 키워드로 검색한다.")
    @SecurityRequirement(name = "Authorization")
    @GetMapping("/shortform")
    fun findShortformByKeyword(
        @CurrentMember member: Member, @RequestParam keyword: String,
        @RequestParam(required = false, defaultValue = "0") offset: Int,
        @RequestParam(required = false, defaultValue = "20") pageSize: Int,
    ): ApiResponse<Slice<ShortFormSimpleResponse>> {
        return ApiResponse.success(searchService.findShortformByKeyword(keyword = keyword, offset, pageSize))
    }
}