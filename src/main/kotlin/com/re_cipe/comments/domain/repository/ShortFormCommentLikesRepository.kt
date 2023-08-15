package com.re_cipe.comments.domain.repository

import com.re_cipe.comments.domain.ShortFormCommentsLikes
import org.springframework.data.jpa.repository.JpaRepository

interface ShortFormCommentLikesRepository : JpaRepository<ShortFormCommentsLikes, Long> {
    fun existsByLikedByIdAndCommentsId(memberId: Long, shortformId: Long): Boolean
    fun findByCommentsIdAndLikedById(commentId: Long, memberId: Long): ShortFormCommentsLikes
}