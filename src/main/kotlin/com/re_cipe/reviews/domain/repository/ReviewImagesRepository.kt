package com.re_cipe.reviews.domain.repository

import com.re_cipe.image.ReviewImages
import org.springframework.data.jpa.repository.JpaRepository

interface ReviewImagesRepository : JpaRepository<ReviewImages, Long> {
}