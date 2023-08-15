package com.re_cipe.comments.domain.repository

import com.re_cipe.comments.domain.Comments
import org.springframework.data.jpa.repository.JpaRepository

interface CommentsRepository: JpaRepository<Comments, Long>, CommentsRepositoryCustom {
}