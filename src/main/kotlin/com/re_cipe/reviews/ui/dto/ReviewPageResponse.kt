package com.re_cipe.reviews.ui.dto

data class ReviewPageResponse(
    val review_total_rating: Double,
    val review_images: MutableList<String>,
    val reviewScores: ReviewScores,
    val review_counts: Int,
    val review_response_list: MutableList<ReviewResponse>
)