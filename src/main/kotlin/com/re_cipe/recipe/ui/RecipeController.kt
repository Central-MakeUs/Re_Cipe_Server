package com.re_cipe.recipe.ui

import com.re_cipe.global.annotation.CurrentMember
import com.re_cipe.global.response.ApiResponse
import com.re_cipe.member.domain.Member
import com.re_cipe.recipe.service.RecipeService
import com.re_cipe.recipe.ui.dto.RecipeDetailResponse
import com.re_cipe.recipe.ui.dto.RecipeCreateRequest
import com.re_cipe.recipe.ui.dto.RecipeResponse
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import org.springframework.data.domain.*
import org.springframework.web.bind.annotation.*

@RequestMapping("/api/v1/recipes")
@RestController
@Api(tags = ["recipe api"], description = "Re:cipe API")
class RecipeController(
    private val recipeService: RecipeService,
) {
    @ApiOperation(value = "레시피 목록을 조회한다.")
    @SecurityRequirement(name = "Authorization")
    @GetMapping
    fun getRecipeLists(
        @Parameter(
            name = "sort",
            description = "레시피 정렬 방식",
            example = "latest",
            schema = Schema(allowableValues = ["latest", "popular", "shortest"])
        )
        @RequestParam(required = false, defaultValue = "latest") sort: String,
        @RequestParam(required = false, defaultValue = "0") offset: Int,
        @RequestParam(required = false, defaultValue = "20") pageSize: Int,
    ): ApiResponse<Slice<RecipeResponse>> {
        val recipes = when (sort) {
            "latest" -> recipeService.getRecipesByLatest(offset, pageSize)
            "popular" -> recipeService.getRecipesByPopular(offset, pageSize)
            "shortest" -> recipeService.getRecipesByShortestTime(offset, pageSize)
            else -> recipeService.getRecipesByLatest(offset, pageSize)
        }
        return ApiResponse.success(recipes)
    }

    @ApiOperation(value = "레시피 상세 조회 api")
    @SecurityRequirement(name = "Authorization")
    @GetMapping("/{recipe-id}/detail")
    fun getRecipeDetail(
        @CurrentMember member: Member,
        @PathVariable("recipe-id") recipeId: Long
    ): ApiResponse<RecipeDetailResponse> {
        return ApiResponse.success(recipeService.getRecipeDetail(recipeId, member.id))
    }

    @ApiOperation(value = "레시피 생성. 반환값은 레시피 id")
    @SecurityRequirement(name = "Authorization")
    @PostMapping
    fun createRecipe(
        @CurrentMember member: Member,
        @RequestBody recipeCreateRequest: RecipeCreateRequest
    ): ApiResponse<Long> {

        return ApiResponse.success(recipeService.createRecipe(recipeCreateRequest, member))
    }

    @ApiOperation(value = "사용자의 저장된 레시피 조회", notes = "Access Token 필요")
    @SecurityRequirement(name = "Authorization")
    @GetMapping("/saved")
    fun getUserSavedRecipes(
        @CurrentMember member: Member
    ): ApiResponse<List<RecipeResponse>> {
        return ApiResponse.success(recipeService.findUserSavedRecipe(member.id))
    }

    @ApiOperation(value = "사용자가 작성한 레시피 조회", notes = "Access Token 필요")
    @SecurityRequirement(name = "Authorization")
    @GetMapping("/written")
    fun getUserWrittenRecipes(
        @CurrentMember member: Member
    ): ApiResponse<List<RecipeResponse>> {
        return ApiResponse.success(recipeService.findMyRecipes(member.id))
    }

    @ApiOperation(value = "나의 레시피 삭제", notes = "Access Token 필요")
    @SecurityRequirement(name = "Authorization")
    @DeleteMapping("/{recipe-id}")
    fun deleteMyRecipe(
        @CurrentMember member: Member,
        @PathVariable("recipe-id") recipeId: Long,
    ): ApiResponse<Boolean> {
        return ApiResponse.success(recipeService.deleteMyRecipe(memberId = member.id, recipeId = recipeId))
    }

    @ApiOperation(value = "레시피 저장", notes = "Access Token 필요")
    @SecurityRequirement(name = "Authorization")
    @PostMapping("/{recipe-id}/save")
    fun saveRecipe(
        @CurrentMember member: Member,
        @PathVariable("recipe-id") recipeId: Long
    ): ApiResponse<Boolean> {
        return ApiResponse.success(recipeService.saveRecipe(member = member, recipeId = recipeId))
    }

    @ApiOperation(value = "레시피 좋아요", notes = "Access Token 필요")
    @SecurityRequirement(name = "Authorization")
    @PostMapping("/{recipe-id}/like")
    fun likeRecipe(
        @CurrentMember member: Member,
        @PathVariable("recipe-id") recipeId: Long
    ): ApiResponse<Boolean> {
        return ApiResponse.success(recipeService.likeRecipe(member = member, recipeId = recipeId))
    }

    @ApiOperation(value = "레시피 저장 취소", notes = "Access Token 필요")
    @SecurityRequirement(name = "Authorization")
    @PostMapping("/{recipe-id}/unsave")
    fun unsaveRecipe(
        @CurrentMember member: Member,
        @PathVariable("recipe-id") recipeId: Long
    ): ApiResponse<Boolean> {
        return ApiResponse.success(recipeService.unsaveRecipe(member = member, recipeId = recipeId))
    }

    @ApiOperation(value = "레시피 좋아요 취소", notes = "Access Token 필요")
    @SecurityRequirement(name = "Authorization")
    @PostMapping("/{recipe-id}/unlike")
    fun unlikeRecipe(
        @CurrentMember member: Member,
        @PathVariable("recipe-id") recipeId: Long
    ): ApiResponse<Boolean> {
        return ApiResponse.success(recipeService.unlikeRecipe(member = member, recipeId = recipeId))
    }

}