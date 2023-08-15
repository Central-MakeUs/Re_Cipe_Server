package com.re_cipe.replies.domain.repository

import com.re_cipe.replies.domain.ReplyLikes
import org.springframework.data.jpa.repository.JpaRepository

interface RepliesLikedRepository : JpaRepository<ReplyLikes, Long> {
    fun existsByLikedByIdAndRepliesId(memberId: Long, replyId: Long): Boolean
}
