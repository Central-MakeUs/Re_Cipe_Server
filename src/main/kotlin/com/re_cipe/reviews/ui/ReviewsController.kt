package com.re_cipe.reviews.ui

import com.re_cipe.global.annotation.CurrentMember
import com.re_cipe.global.response.ApiResponse
import com.re_cipe.member.domain.Member
import com.re_cipe.recipe.ui.dto.RecipeResponse
import com.re_cipe.reviews.service.ReviewsService
import com.re_cipe.reviews.ui.dto.ReviewResponse
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.media.Schema
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Slice
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/api/v1/reviews")
@RestController
@Api(tags = ["review api"], description = "Re:cipe API")
class ReviewsController(private val reviewsService: ReviewsService) {
//    @ApiOperation(value = "내가 작성한 리뷰를 조회한다.")
//    @GetMapping
//    fun getMyReviews(
//    ): ApiResponse<List> {
//        val recipes = when (sort) {
//            "latest" -> recipeService.getRecipesByLatest(pageable)
//            "popular" -> recipeService.getRecipesByPopular(pageable)
//            "shortest" -> recipeService.getRecipesByShortestTime(pageable)
//            else -> recipeService.getRecipesByLatest(pageable)
//        }
//        return ApiResponse.success(recipes)
//    }

    @ApiOperation(value = "레시피에 존재하는 모든 리뷰를 조회한다.")
    @GetMapping("/{review-id}")
    fun getAllReviews(
        @Parameter(
            name = "sort",
            description = "레시피 정렬 방식",
            example = "popular",
            schema = Schema(allowableValues = ["latest", "popular"])
        )
        @RequestParam(required = false, defaultValue = "latest") sort: String,
        pageable: Pageable,
        @PathVariable("review-id") recipeId: Long,
        @CurrentMember member: Member
    ): ApiResponse<Slice<ReviewResponse>> {
        val reviews = when (sort) {
            "latest" -> reviewsService.getReviewsByLatest(pageable, recipeId, member)
            "popular" -> reviewsService.getReviewsByPopular(pageable, recipeId, member)
            else -> reviewsService.getReviewsByPopular(pageable, recipeId, member)
        }
        return ApiResponse.success(reviews)
    }
}