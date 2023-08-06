package com.re_cipe.reviews.domain.repository

import com.re_cipe.reviews.domain.Reviews
import org.springframework.data.jpa.repository.JpaRepository

interface ReviewsRepository: JpaRepository<Reviews, Long>, ReviewsRepositoryCustom {
}