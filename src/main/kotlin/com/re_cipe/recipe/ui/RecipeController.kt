package com.re_cipe.recipe.ui

import com.re_cipe.global.annotation.CurrentMember
import com.re_cipe.global.response.ApiResponse
import com.re_cipe.member.domain.Member
import com.re_cipe.recipe.service.RecipeService
import com.re_cipe.recipe.ui.dto.RecipeDetailResponse
import com.re_cipe.recipe.ui.dto.RecipeRequest
import com.re_cipe.recipe.ui.dto.RecipeResponse
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Slice
import org.springframework.web.bind.annotation.*

@RequestMapping("/api/v1/recipes")
@RestController
@Api(tags = ["recipe api"], description = "Re:cipe API")
class RecipeController(
    private val recipeService: RecipeService,
) {
    @ApiOperation(value = "레시피 목록을 조회한다.")
    @GetMapping
    fun getRecipeLists(
        pageable: Pageable,
        @Parameter(
            name = "sort",
            description = "레시피 정렬 방식",
            example = "latest",
            schema = Schema(allowableValues = ["latest", "popular", "shortest"])
        )
        @RequestParam(required = false, defaultValue = "latest") sort: String,
    ): ApiResponse<Slice<RecipeResponse>> {
        val recipes = when (sort) {
            "latest" -> recipeService.getRecipesByLatest(pageable)
            "popular" -> recipeService.getRecipesByPopular(pageable)
            "shortest" -> recipeService.getRecipesByShortestTime(pageable)
            else -> recipeService.getRecipesByLatest(pageable)
        }
        return ApiResponse.success(recipes)
    }

    @ApiOperation(value = "레시피 상세 조회 api")
    @GetMapping("/{recipe-id}/detail")
    fun getRecipeDetail(): ApiResponse<RecipeDetailResponse> {
        TODO()
    }

    @ApiOperation(value = "레시피 생성한다.")
    @PostMapping
    fun createRecipe(
        @RequestBody recipeRequest: RecipeRequest
    ): ApiResponse<String> {
        TODO()
    }

    @ApiOperation(value="사용자의 저장된 레시피 조회", notes = "Access Token 필요")
    @SecurityRequirement(name = "Authorization")
    @GetMapping("/saved")
    fun getUserSavedRecipes(
        @CurrentMember member: Member
    ): ApiResponse<List<RecipeResponse>> {
        return ApiResponse.success(recipeService.findUserSavedRecipe(member.id))
    }

    @ApiOperation(value="사용자가 작성한 레시피 조회", notes = "Access Token 필요")
    @SecurityRequirement(name = "Authorization")
    @GetMapping("/written")
    fun getUserWrittenRecipes(
        @CurrentMember member: Member
    ): ApiResponse<List<RecipeResponse>> {
        return ApiResponse.success(recipeService.findMyRecipes(member.id))
    }

    @ApiOperation(value="나의 레시피 삭제", notes = "Access Token 필요")
    @SecurityRequirement(name = "Authorization")
    @DeleteMapping("/{recipe-id}")
    fun deleteMyRecipe(
        @CurrentMember member: Member,
        @PathVariable("recipe-id") recipeId: Long,
    ): ApiResponse<Boolean> {
        return ApiResponse.success(recipeService.deleteMyRecipe(memberId = member.id, recipeId = recipeId))
    }
}