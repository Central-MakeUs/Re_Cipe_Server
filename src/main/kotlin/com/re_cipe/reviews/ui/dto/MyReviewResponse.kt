package com.re_cipe.reviews.ui.dto

import com.re_cipe.reviews.domain.Reviews
import java.time.LocalDateTime

data class MyReviewResponse(
    val review_id: Long,
    val recipe_name: String,
    val review_rating: Int,
    val img_list: List<String>,
    val review_content: String,
    val written_date: LocalDateTime,
    val like_count: Int
) {
    companion object {
        fun of(reviews: Reviews): MyReviewResponse {
            val reviewImages = reviews.images.map { it.image_url ?: "" }
            return MyReviewResponse(
                review_id = reviews.id,
                recipe_name = reviews.recipe.name,
                review_rating = reviews.rating,
                img_list = reviewImages,
                review_content = reviews.content,
                written_date = reviews.modifiedAt,
                like_count = reviews.likes.size
            )
        }

    }
}