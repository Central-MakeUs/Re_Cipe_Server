package com.re_cipe.reviews.ui.dto

import com.re_cipe.reviews.domain.Reviews
import java.time.LocalDateTime

data class ReviewResponse(
    val review_id: Long,
    val writtenby: String,
    val rating: Int,
    val review_title: String,
    val review_content: String,
    val modified_at: LocalDateTime,
    val review_images: MutableList<String>,
    val liked: Boolean
) {
    companion object {
        private fun createReviewTitle(content: String): String {
            if (content.length < 10)
                return content
            return content.take(10) + "..."
        }

        fun of(reviews: Reviews, liked: Boolean): ReviewResponse {
            val reviewImages = reviews.images.map { it.image_url ?: "" }
            return ReviewResponse(
                review_id = reviews.id,
                writtenby = reviews.writtenBy.nickname,
                rating = reviews.rating,
                review_title = createReviewTitle(reviews.content),
                review_content = reviews.content,
                modified_at = reviews.modifiedAt,
                review_images = reviewImages.toMutableList(),
                liked = liked
            )
        }

    }
}