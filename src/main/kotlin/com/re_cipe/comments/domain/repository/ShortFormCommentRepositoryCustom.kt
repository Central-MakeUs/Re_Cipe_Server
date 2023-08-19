package com.re_cipe.comments.domain.repository

import com.re_cipe.comments.domain.Comments
import com.re_cipe.comments.domain.ShortFormComments
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Slice
import org.springframework.data.jpa.repository.JpaRepository

interface ShortFormCommentRepositoryCustom {
    fun findCommentsByShortFormId(shortFormId: Long, pageable: Pageable): Slice<ShortFormComments>
    fun deleteComment(commendId: Long): Boolean
}