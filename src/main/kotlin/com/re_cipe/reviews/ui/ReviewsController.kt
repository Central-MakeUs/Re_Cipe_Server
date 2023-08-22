package com.re_cipe.reviews.ui

import com.re_cipe.global.annotation.CurrentMember
import com.re_cipe.global.response.ApiResponse
import com.re_cipe.member.domain.Member
import com.re_cipe.recipe.ui.dto.RecipeResponse
import com.re_cipe.reviews.service.ReviewsService
import com.re_cipe.reviews.ui.dto.MyReviewResponse
import com.re_cipe.reviews.ui.dto.ReviewCreateRequest
import com.re_cipe.reviews.ui.dto.ReviewResponse
import com.re_cipe.reviews.ui.dto.ReviewScores
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Slice
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/api/v1/reviews")
@RestController
@Api(tags = ["review api"], description = "Re:cipe API")
class ReviewsController(private val reviewsService: ReviewsService) {
    @ApiOperation(value = "내가 작성한 리뷰를 조회한다.", notes = "Access Token 필요")
    @SecurityRequirement(name = "Authorization")
    @GetMapping("/my")
    fun getMyReviews(
        @CurrentMember member: Member
    ): ApiResponse<List<MyReviewResponse>> {
        return ApiResponse.success(reviewsService.getMyReviews(member))
    }

    @ApiOperation(value = "레시피의 리뷰 점수들-레시피의 리뷰 상세 페이지 상단", notes = "Access Token 필요")
    @SecurityRequirement(name = "Authorization")
    @GetMapping("/recipe/{recipe-id}/scores")
    fun getReviewsScore(
        @PathVariable("recipe-id") recipeId: Long,
        @CurrentMember member: Member
    ): ApiResponse<ReviewScores> {
        return ApiResponse.success(reviewsService.getReviewScores(recipeId))
    }

    @ApiOperation(value = "레시피에 존재하는 모든 리뷰를 조회한다-레시피의 리뷰 상세 페이지 하단", notes = "Access Token 필요")
    @SecurityRequirement(name = "Authorization")
    @GetMapping("/recipe/{recipe-id}")
    fun getAllReviews(
        @Parameter(
            name = "sort",
            description = "레시피 정렬 방식",
            example = "popular",
            schema = Schema(allowableValues = ["latest", "popular"])
        )
        @RequestParam(required = false, defaultValue = "latest") sort: String,
        pageable: Pageable,
        @PathVariable("recipe-id") recipeId: Long,
        @CurrentMember member: Member
    ): ApiResponse<Slice<ReviewResponse>> {
        val reviews = when (sort) {
            "latest" -> reviewsService.getReviewsByLatest(pageable, recipeId, member)
            "popular" -> reviewsService.getReviewsByPopular(pageable, recipeId, member)
            else -> reviewsService.getReviewsByPopular(pageable, recipeId, member)
        }
        return ApiResponse.success(reviews)
    }

    @ApiOperation(value = "레시피에 리뷰를 등록한다.", notes = "Access Token 필요")
    @SecurityRequirement(name = "Authorization")
    @PostMapping("/recipe/{recipe-id}")
    fun createReviews(
        @PathVariable("recipe-id") recipeId: Long,
        @CurrentMember member: Member,
        @RequestBody reviewCreateRequest: ReviewCreateRequest
    ): ApiResponse<Long> {
        return ApiResponse.success(
            reviewsService.createReview(
                recipeId = recipeId,
                member = member,
                reviewCreateRequest = reviewCreateRequest
            )
        )
    }

    @ApiOperation(value = "리뷰를 삭제한다.", notes = "Access Token 필요")
    @SecurityRequirement(name = "Authorization")
    @DeleteMapping("/{review-id}")
    fun deleteReviews(
        @PathVariable("review-id") reviewId: Long,
        @CurrentMember member: Member
    ): ApiResponse<Boolean> {
        return ApiResponse.success(reviewsService.deleteReview(reviewId = reviewId, member = member))
    }

    @ApiOperation(value = "리뷰를 신고한다.", notes = "Access Token 필요")
    @SecurityRequirement(name = "Authorization")
    @PostMapping("/{review-id}/report")
    fun reportReview(
        @PathVariable("review-id") reviewId: Long,
        @CurrentMember member: Member
    ): ApiResponse<Boolean> {
        return ApiResponse.success(reviewsService.reportReview(reviewId = reviewId, member = member))
    }
}