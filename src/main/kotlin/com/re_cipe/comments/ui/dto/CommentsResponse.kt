package com.re_cipe.comments.ui.dto

import com.re_cipe.comments.domain.Comments
import com.re_cipe.comments.domain.ShortFormComments
import com.re_cipe.replies.ui.dto.ReplyResponse
import java.time.LocalDateTime
import kotlin.streams.toList

data class CommentsResponse(
    val comment_id: Long,
    val comment_content: String,
    val comment_writtenby: String,
    val comment_likes: Int,
    val replyList: List<ReplyResponse>,
    val is_liked: Boolean,
    val created_at: LocalDateTime
) {
    companion object {
        fun of(comment: Comments, comment_is_liked: Boolean): CommentsResponse {
            return CommentsResponse(
                comment_id = comment.id,
                comment_content = comment.content,
                comment_writtenby = comment.writtenBy.nickname,
                comment_likes = comment.likes.size,
                replyList = comment.replyList.stream()
                    .filter { reply -> !reply.isDeleted }
                    .map { reply -> ReplyResponse.of(reply, false) }
                    .toList(),
                is_liked = comment_is_liked,
                created_at = comment.createdAt
            )
        }

        fun of(comment: ShortFormComments, comment_is_liked: Boolean): CommentsResponse {
            return CommentsResponse(
                comment_id = comment.id,
                comment_content = comment.content,
                comment_writtenby = comment.writtenBy.nickname,
                comment_likes = comment.likes.size,
                replyList = comment.replyList.stream()
                    .filter { reply -> !reply.isDeleted }
                    .map { reply -> ReplyResponse.of(reply, false) }
                    .toList(),
                is_liked = comment_is_liked,
                created_at = comment.createdAt
            )
        }
    }
}