package com.re_cipe.reviews.service

import com.re_cipe.member.domain.Member
import com.re_cipe.reviews.domain.Reviews
import com.re_cipe.reviews.domain.repository.ReviewsRepository
import com.re_cipe.reviews.ui.dto.ReviewResponse
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Slice
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class ReviewsService(
    private val reviewsRepository: ReviewsRepository,
) {
    fun getReviewsByLatest(pageable: Pageable, recipeId: Long, member: Member): Slice<ReviewResponse> {

        return reviewsRepository.findReviewsByLatest(pageable, recipeId)
            .map { review -> ReviewResponse.of(review, checkLikedReview(review, member)) }
    }

    fun getReviewsByPopular(pageable: Pageable, recipeId: Long, member: Member): Slice<ReviewResponse> {
        return reviewsRepository.findReviewsByPopular(pageable, recipeId)
            .map { review -> ReviewResponse.of(review, checkLikedReview(review, member)) }
    }

    private fun checkLikedReview(reviews: Reviews, member: Member): Boolean {
        return reviews.likes.stream()
            .anyMatch { reviewLike -> reviewLike.likedBy.equals(member) }
    }
}