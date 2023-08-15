package com.re_cipe.comments.domain.repository

import com.re_cipe.comments.domain.ShortFormComments
import org.springframework.data.jpa.repository.JpaRepository

interface ShortFormCommentRepository : JpaRepository<ShortFormComments, Long>, ShortFormCommentRepositoryCustom {
}