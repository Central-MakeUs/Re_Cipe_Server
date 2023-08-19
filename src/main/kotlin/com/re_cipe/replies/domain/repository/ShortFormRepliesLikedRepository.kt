package com.re_cipe.replies.domain.repository

import com.re_cipe.replies.domain.ReplyLikes
import com.re_cipe.replies.domain.ShortFormReplyLikes
import org.springframework.data.jpa.repository.JpaRepository

interface ShortFormRepliesLikedRepository : JpaRepository<ShortFormReplyLikes, Long> {
    fun existsByLikedByIdAndRepliesId(memberId: Long, replyId: Long): Boolean
    fun findByLikedByIdAndRepliesId(memberId: Long, replyId: Long): ShortFormReplyLikes
}
