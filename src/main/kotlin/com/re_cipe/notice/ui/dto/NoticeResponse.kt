package com.re_cipe.notice.ui.dto

import com.re_cipe.notice.domain.Notice

data class NoticeResponse(
    val id: Long,
    val title: String,
    val content: String
) {
    companion object {
        fun of(notice: Notice): NoticeResponse {
            return NoticeResponse(notice.id, notice.title, notice.content)
        }
    }
}