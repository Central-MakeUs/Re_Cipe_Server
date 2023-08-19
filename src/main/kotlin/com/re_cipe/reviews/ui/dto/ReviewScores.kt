package com.re_cipe.reviews.ui.dto

import com.re_cipe.reviews.domain.Reviews

data class ReviewScores(
    var fivePoint: Int,
    var fourPoint: Int,
    var threePoint: Int,
    var twoPoint: Int,
    var onePoint: Int,
    var totalRating: Double
)