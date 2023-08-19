package com.re_cipe.reviews.ui.dto

data class ReviewCreateRequest(
    val review_content: String,
    val review_rating: Int,
    val review_imgs: List<String>
)