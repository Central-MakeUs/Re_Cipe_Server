package com.re_cipe.replies.ui

import com.re_cipe.comments.ui.dto.CommentCreateRequest
import com.re_cipe.global.annotation.CurrentMember
import com.re_cipe.global.response.ApiResponse
import com.re_cipe.member.domain.Member
import com.re_cipe.replies.service.ReplyService
import com.re_cipe.replies.ui.dto.ReplyCreateRequest
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import org.springframework.web.bind.annotation.*

@RequestMapping("/api/v1/replies")
@RestController
@Api(tags = ["reply api"], description = "Re:cipe API")
class ReplyController (
    val replyService: ReplyService
){
    @ApiOperation(value = "대댓글 등록, 대댓글 id 반환")
    @PostMapping("/recipe/comment/{comment-id}")
    fun createReply(
        @PathVariable("comment-id") commentId: Long,
        @CurrentMember member: Member,
        @RequestBody replyCreateRequest: ReplyCreateRequest
    ): ApiResponse<Long> {
        return ApiResponse.success(replyService.createOneReply(commentId, member, replyCreateRequest))
    }

    @ApiOperation(value = "대댓글 삭제")
    @DeleteMapping("/recipe/{reply-id}")
    fun deleteReply(
        @PathVariable("reply-id") replyId: Long,
        @CurrentMember member: Member,
    ): ApiResponse<Boolean> {
        return ApiResponse.success(replyService.deleteReply(replyId, member))
    }

    @ApiOperation(value = "대댓글에 좋아요")
    @GetMapping("/recipe/like/{reply-id}")
    fun likeReply(
        @PathVariable("reply-id") replyId: Long,
        @CurrentMember member: Member,
    ): ApiResponse<Boolean> {
        return ApiResponse.success(replyService.likeReply(replyId, member))
    }

    @ApiOperation(value = "대댓글에 좋아요 취소")
    @GetMapping("/recipe/unlike/{reply-id}")
    fun unlikeReply(
        @PathVariable("reply-id") replyId: Long,
        @CurrentMember member: Member,
    ): ApiResponse<Boolean> {
        return ApiResponse.success(replyService.unlikeReply(replyId, member))
    }

    @ApiOperation(value = "숏폼 대댓글 등록, 대댓글 id 반환")
    @PostMapping("/shortform/comment/{shortform-comment-id}")
    fun createShortformReply(
        @PathVariable("shortform-comment-id") commentId: Long,
        @CurrentMember member: Member,
        @RequestBody replyCreateRequest: ReplyCreateRequest
    ): ApiResponse<Long> {
        return ApiResponse.success(replyService.createShortFormReply(commentId, member, replyCreateRequest))
    }

    @ApiOperation(value = "숏폼 대댓글 삭제")
    @DeleteMapping("/shortform/{shortform-reply-id}")
    fun deleteShortformReply(
        @PathVariable("shortform-reply-id") replyId: Long,
        @CurrentMember member: Member,
    ): ApiResponse<Boolean> {
        return ApiResponse.success(replyService.deleteShortFormReply(replyId, member))
    }

    @ApiOperation(value = "숏폼 대댓글에 좋아요")
    @GetMapping("/shortform/like/{shortform-reply-id}")
    fun likeShortFormReply(
        @PathVariable("shortform-reply-id") replyId: Long,
        @CurrentMember member: Member,
    ): ApiResponse<Boolean> {
        return ApiResponse.success(replyService.likeShortFormReply(replyId, member))
    }

    @ApiOperation(value = "숏폼 대댓글에 좋아요 취소")
    @GetMapping("/shortform/unlike/{shortform-reply-id}")
    fun unlikeShortFormReply(
        @PathVariable("shortform-reply-id") replyId: Long,
        @CurrentMember member: Member,
    ): ApiResponse<Boolean> {
        return ApiResponse.success(replyService.unlikeShortFormReply(replyId, member))
    }
}