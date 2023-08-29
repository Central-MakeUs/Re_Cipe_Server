package com.re_cipe.replies.service

import com.re_cipe.comments.domain.repository.CommentsRepository
import com.re_cipe.comments.domain.repository.ShortFormCommentRepository
import com.re_cipe.exception.BusinessException
import com.re_cipe.exception.ErrorCode
import com.re_cipe.global.util.SlackUtil
import com.re_cipe.member.domain.Member
import com.re_cipe.replies.domain.Replies
import com.re_cipe.replies.domain.ReplyLikes
import com.re_cipe.replies.domain.ShortFormReplies
import com.re_cipe.replies.domain.ShortFormReplyLikes
import com.re_cipe.replies.domain.repository.RepliesLikedRepository
import com.re_cipe.replies.domain.repository.RepliesRepository
import com.re_cipe.replies.domain.repository.ShortFormRepliesLikedRepository
import com.re_cipe.replies.domain.repository.ShortFormRepliesRepository
import com.re_cipe.replies.ui.dto.ReplyCreateRequest
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class ReplyService(
    val replyRepository: RepliesRepository,
    val commentRepository: CommentsRepository,
    val repliesLikedRepository: RepliesLikedRepository,
    val shortFormCommentRepository: ShortFormCommentRepository,
    val shortFormRepliesRepository: ShortFormRepliesRepository,
    val shortFormRepliesLikedRepository: ShortFormRepliesLikedRepository,
    val slackUtil: SlackUtil
) {
    @Transactional
    fun createOneReply(commentId: Long, member: Member, replyCreateRequest: ReplyCreateRequest): Long {
        val comments = commentRepository.findById(commentId)
            .orElseThrow { BusinessException(ErrorCode.NO_COMMENT_FOUND) }
        var replies = Replies(
            comment = comments,
            writtenBy = member,
            content = replyCreateRequest.content
        )
        replies = replyRepository.save(replies)
        comments.replyList.add(replies)
        return replies.id
    }

    @Transactional
    fun deleteReply(replyId: Long, member: Member): Boolean {
        val reply = replyRepository.findById(replyId).orElseThrow { BusinessException(ErrorCode.NO_REPLY_FOUND) }
        if (reply.writtenBy.id != member.id) {
            throw BusinessException(ErrorCode.NO_AUTHENTICATION)
        }
        reply.comment.replyList.remove(reply)
        return replyRepository.deleteReply(replyId)
    }

    @Transactional
    fun likeReply(replyId: Long, member: Member): Boolean {
        val reply = replyRepository.findById(replyId).orElseThrow { BusinessException(ErrorCode.NO_REPLY_FOUND) }
        if (repliesLikedRepository.existsByLikedByIdAndRepliesId(memberId = member.id, replyId = replyId)) {
            throw BusinessException(ErrorCode.ALREADY_LIKED_REPLY)
        }
        val replyLikes = ReplyLikes(likedBy = member, replies = reply)
        repliesLikedRepository.save(replyLikes)
        reply.likes.add(replyLikes)
        return true
    }

    @Transactional
    fun unlikeReply(replyId: Long, member: Member): Boolean {
        val replyLikes = repliesLikedRepository.findByLikedByIdAndRepliesId(member.id, replyId)
        val reply = replyRepository.findById(replyId).orElseThrow { BusinessException(ErrorCode.NO_REPLY_FOUND) }
        repliesLikedRepository.delete(replyLikes)
        reply.likes.remove(replyLikes)
        return true
    }

    @Transactional
    fun reportReply(replyId: Long, member: Member): Boolean {
        slackUtil.sendReplyReport(
            member = member,
            replies = replyRepository.findById(replyId).orElseThrow { BusinessException(ErrorCode.NO_REPLY_FOUND) })

        replyRepository.deleteReply(replyId)
        return true
    }

    @Transactional
    fun createShortFormReply(commentId: Long, member: Member, replyCreateRequest: ReplyCreateRequest): Long {
        val comments = shortFormCommentRepository.findById(commentId)
            .orElseThrow { BusinessException(ErrorCode.NO_COMMENT_FOUND) }
        var replies = ShortFormReplies(
            comment = comments,
            writtenBy = member,
            content = replyCreateRequest.content
        )
        replies = shortFormRepliesRepository.save(replies)
        comments.replyList.add(replies)
        return replies.id
    }

    @Transactional
    fun deleteShortFormReply(replyId: Long, member: Member): Boolean {
        val reply =
            shortFormRepliesRepository.findById(replyId).orElseThrow { BusinessException(ErrorCode.NO_REPLY_FOUND) }
        if (reply.writtenBy.id != member.id) {
            throw BusinessException(ErrorCode.NO_AUTHENTICATION)
        }
        reply.comment.replyList.remove(reply)
        return shortFormRepliesRepository.deleteReply(replyId)
    }

    @Transactional
    fun likeShortFormReply(replyId: Long, member: Member): Boolean {
        val reply =
            shortFormRepliesRepository.findById(replyId).orElseThrow { BusinessException(ErrorCode.NO_REPLY_FOUND) }
        if (shortFormRepliesLikedRepository.existsByLikedByIdAndRepliesId(memberId = member.id, replyId = replyId)) {
            throw BusinessException(ErrorCode.ALREADY_LIKED_REPLY)
        }
        val replyLikes = ShortFormReplyLikes(likedBy = member, replies = reply)
        shortFormRepliesLikedRepository.save(replyLikes)
        reply.likes.add(replyLikes)
        return true
    }

    @Transactional
    fun unlikeShortFormReply(replyId: Long, member: Member): Boolean {
        val replyLikes = shortFormRepliesLikedRepository.findByLikedByIdAndRepliesId(member.id, replyId)
        if (replyLikes == null) {
            throw BusinessException(ErrorCode.LIKED_ERROR)
        }
        val reply =
            shortFormRepliesRepository.findById(replyId).orElseThrow { BusinessException(ErrorCode.NO_REPLY_FOUND) }
        shortFormRepliesLikedRepository.delete(replyLikes)
        reply.likes.remove(replyLikes)
        return true
    }

    @Transactional
    fun reportShortFormReply(replyId: Long, member: Member): Boolean {
        val replies = shortFormRepliesRepository.findById(replyId)
            .orElseThrow { BusinessException(ErrorCode.NO_REPLY_FOUND) }
        slackUtil.sendShortFormReplyReport(
            member = member,
            replies = replies
        )
        replies.comment.replyList.remove(replies)
        shortFormRepliesRepository.deleteReply(replyId)
        return true
    }
}