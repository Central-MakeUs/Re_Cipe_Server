package com.re_cipe.comments.domain.repository

import com.re_cipe.comments.domain.CommentLikes
import org.springframework.data.jpa.repository.JpaRepository

interface CommentLikesRepository : JpaRepository<CommentLikes, Long> {
    fun existsByCommentsIdAndLikedById(commendId: Long, memberId: Long): Boolean
    fun findByCommentsIdAndLikedById(commendId: Long, memberId: Long): CommentLikes
}