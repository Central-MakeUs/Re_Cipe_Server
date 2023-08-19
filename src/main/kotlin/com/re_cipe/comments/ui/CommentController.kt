package com.re_cipe.comments.ui

import com.re_cipe.comments.service.CommentService
import com.re_cipe.comments.ui.dto.CommentCreateRequest
import com.re_cipe.comments.ui.dto.CommentsResponse
import com.re_cipe.global.annotation.CurrentMember
import com.re_cipe.global.response.ApiResponse
import com.re_cipe.member.domain.Member
import com.re_cipe.reviews.ui.dto.ReviewResponse
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.media.Schema
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Slice
import org.springframework.web.bind.annotation.*


@RequestMapping("/api/v1/comments")
@RestController
@Api(tags = ["comment api"], description = "Re:cipe API")
class CommentController(private val commentService: CommentService) {
    @ApiOperation(value = "레시피에 존재하는 모든 댓글 조회")
    @GetMapping("/recipe/{recipe-id}")
    fun getAllComments(
        @PathVariable("recipe-id") recipeId: Long,
        @RequestParam(required = false, defaultValue = "0") offset: Int,
        @RequestParam(required = false, defaultValue = "20") pageSize: Int,
        @CurrentMember member: Member
    ): ApiResponse<Slice<CommentsResponse>> {
        return ApiResponse.success(commentService.findAllRecipeComments(recipeId, offset, pageSize, member))
    }

    @ApiOperation(value = "레시피에 댓글 생성, 댓글 id 반환")
    @PostMapping("/recipe/{recipe-id}")
    fun createComment(
        @PathVariable("recipe-id") recipeId: Long,
        @CurrentMember member: Member,
        @RequestBody commentCreateRequest: CommentCreateRequest
    ): ApiResponse<Long> {
        return ApiResponse.success(commentService.createOneComment(recipeId, member, commentCreateRequest))
    }

    @ApiOperation(value = "레시피의 자신 댓글 삭제")
    @DeleteMapping("/recipe/{comment-id}")
    fun deleteRecipeComment(
        @PathVariable("comment-id") commentId: Long,
        @CurrentMember member: Member
    ): ApiResponse<Boolean> {
        return ApiResponse.success(commentService.deleteComment(commentId, member))
    }

    @ApiOperation(value = "레시피의 댓글 좋아요")
    @GetMapping("/recipe/like/{comment-id}")
    fun likeComment(
        @PathVariable("comment-id") commentId: Long,
        @CurrentMember member: Member
    ): ApiResponse<Boolean> {
        return ApiResponse.success(commentService.likeComment(commentId, member))
    }

    @ApiOperation(value = "레시피의 댓글 좋아요 취소")
    @GetMapping("/recipe/unlike/{comment-id}")
    fun unlikeComment(
        @PathVariable("comment-id") commentId: Long,
        @CurrentMember member: Member
    ): ApiResponse<Boolean> {
        return ApiResponse.success(commentService.unlikeComment(commentId, member))
    }

    @ApiOperation(value = "숏폼 레시피에 존재하는 모든 댓글 조회")
    @GetMapping("/shortform/{shortform-id}")
    fun getAllShortFormComments(
        @PathVariable("shortform-id") shortformId: Long,
        @RequestParam(required = false, defaultValue = "0") offset: Int,
        @RequestParam(required = false, defaultValue = "20") pageSize: Int,
        @CurrentMember member: Member
    ): ApiResponse<Slice<CommentsResponse>> {
        return ApiResponse.success(commentService.findAllShortFormComments(shortformId, offset, pageSize, member))
    }

    @ApiOperation(value = "숏폼 레시피의 댓글 생성, 댓글 id 반환")
    @PostMapping("/shortform/{shortform-id}")
    fun createShortformComment(
        @PathVariable("shortform-id") shortformId: Long,
        @CurrentMember member: Member,
        @RequestBody commentCreateRequest: CommentCreateRequest
    ): ApiResponse<Long> {
        return ApiResponse.success(commentService.createOneShortFormComment(shortformId, member, commentCreateRequest))
    }

    @ApiOperation(value = "숏폼레시피의 댓글 좋아요")
    @GetMapping("/shortform/like/{shortform-comment-id}")
    fun likeShortFormComment(
        @PathVariable("shortform-comment-id") commentId: Long,
        @CurrentMember member: Member
    ): ApiResponse<Boolean> {
        return ApiResponse.success(commentService.likeShortFormComment(commentId, member))
    }

    @ApiOperation(value = "숏폼레시피의 댓글 좋아요 취소")
    @GetMapping("/shortform/unlike/{shortform-comment-id}")
    fun unlikeShortFormComment(
        @PathVariable("shortform-comment-id") commentId: Long,
        @CurrentMember member: Member
    ): ApiResponse<Boolean> {
        return ApiResponse.success(commentService.unlikeShortFormComment(commentId, member))
    }

    @ApiOperation(value = "숏폼의 자신 댓글 삭제")
    @DeleteMapping("/shortform/{shortform-comment-id}")
    fun deleteShortFormComment(
        @PathVariable("shortform-comment-id") commentId: Long,
        @CurrentMember member: Member
    ): ApiResponse<Boolean> {
        return ApiResponse.success(commentService.deleteShortFormComment(commentId, member))
    }
}