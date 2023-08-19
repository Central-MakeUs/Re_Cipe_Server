package com.re_cipe.replies.domain.repository

import com.re_cipe.replies.domain.Replies
import com.re_cipe.replies.domain.ShortFormReplies
import org.springframework.data.jpa.repository.JpaRepository

interface ShortFormRepliesRepository : JpaRepository<ShortFormReplies, Long>, ShortFormRepliesRepositoryCustom {
}