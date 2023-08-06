package com.re_cipe.reviews.domain.repository

import com.re_cipe.recipe.domain.Recipe
import com.re_cipe.reviews.domain.Reviews
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Slice

interface ReviewsRepositoryCustom {
    fun findReviewsByLatest(pageable: Pageable, recipeId: Long): Slice<Reviews>
    fun findReviewsByPopular(pageable: Pageable, recipeId: Long): Slice<Reviews>
}