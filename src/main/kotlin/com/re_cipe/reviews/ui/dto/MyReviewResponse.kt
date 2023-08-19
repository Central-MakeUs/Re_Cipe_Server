package com.re_cipe.reviews.ui.dto

import com.re_cipe.reviews.domain.Reviews
import java.time.LocalDateTime

data class MyReviewResponse(
    val recipe_name: String,
    val review_rating: Int,
    val img_list: List<String>,
    val review_content: String,
    val written_date: LocalDateTime
) {
    companion object {
        fun of(reviews: Reviews): MyReviewResponse {
            val reviewImages = reviews.images.map { it.image_url ?: "" }
            return MyReviewResponse(
                recipe_name = "",
                review_rating = reviews.rating,
                img_list = reviewImages,
                review_content = reviews.content,
                written_date = reviews.modifiedAt
            )
        }

    }
}