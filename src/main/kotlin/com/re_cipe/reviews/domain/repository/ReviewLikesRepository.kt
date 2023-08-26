package com.re_cipe.reviews.domain.repository

import com.re_cipe.reviews.domain.ReviewsLikes
import org.springframework.data.jpa.repository.JpaRepository

interface ReviewLikesRepository : JpaRepository<ReviewsLikes, Long> {
    fun existsByLikedByIdAndReviewsId(memberId: Long, reviewId: Long): Boolean
    fun findByLikedByIdAndReviewsId(memberId: Long, reviewId: Long): ReviewsLikes
}