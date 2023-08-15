package com.re_cipe.replies.ui.dto

import com.re_cipe.comments.ui.dto.CommentsResponse
import com.re_cipe.recipe.domain.Recipe
import com.re_cipe.replies.domain.Replies
import com.re_cipe.replies.domain.ShortFormReplies
import com.re_cipe.stage.ui.dto.StageResponse
import java.time.LocalDateTime

data class ReplyResponse(
    val reply_id: Long,
    val reply_content: String,
    val reply_writtenby: String,
    val reply_likes: Int,
    val is_likes: Boolean,
    val created_at: LocalDateTime
) {
    companion object {
        fun of(replies: Replies, is_likes: Boolean): ReplyResponse {
            return ReplyResponse(
                reply_id = replies.id,
                reply_content = replies.content,
                reply_writtenby = replies.writtenBy.nickname,
                reply_likes = replies.likes.size,
                is_likes = is_likes,
                created_at = replies.createdAt
            )
        }

        fun of(replies: ShortFormReplies, is_likes: Boolean): ReplyResponse {
            return ReplyResponse(
                reply_id = replies.id,
                reply_content = replies.content,
                reply_writtenby = replies.writtenBy.nickname,
                reply_likes = replies.likes.size,
                is_likes = is_likes,
                created_at = replies.createdAt
            )
        }
    }
}