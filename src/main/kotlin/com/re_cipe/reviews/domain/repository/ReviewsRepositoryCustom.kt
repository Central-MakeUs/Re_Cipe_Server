package com.re_cipe.reviews.domain.repository

import com.re_cipe.recipe.domain.Recipe
import com.re_cipe.reviews.domain.Reviews
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Slice

interface ReviewsRepositoryCustom {
    fun findReviewsByLatest(pageable: Pageable, recipeId: Long): Slice<Reviews>
    fun findReviewsByPopular(pageable: Pageable, recipeId: Long): Slice<Reviews>
    fun findMyReviews(memberId: Long): List<Reviews>
    fun deleteReview(reviewId: Long): Boolean
    fun findReviewCountByRating(rating: Int, recipeId: Long): Int
    fun findAllRecipePhoto(recipeId: Long): List<String>
}