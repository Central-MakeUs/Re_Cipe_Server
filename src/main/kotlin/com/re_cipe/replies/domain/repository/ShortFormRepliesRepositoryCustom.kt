package com.re_cipe.replies.domain.repository

import com.re_cipe.replies.domain.Replies
import org.springframework.data.jpa.repository.JpaRepository

interface ShortFormRepliesRepositoryCustom {
    fun deleteReply(replyId: Long): Boolean
}