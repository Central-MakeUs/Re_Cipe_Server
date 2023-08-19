package com.re_cipe.comments.service

import com.re_cipe.comments.domain.CommentLikes
import com.re_cipe.comments.domain.Comments
import com.re_cipe.comments.domain.ShortFormComments
import com.re_cipe.comments.domain.ShortFormCommentsLikes
import com.re_cipe.comments.domain.repository.CommentLikesRepository
import com.re_cipe.comments.domain.repository.CommentsRepository
import com.re_cipe.comments.domain.repository.ShortFormCommentLikesRepository
import com.re_cipe.comments.domain.repository.ShortFormCommentRepository
import com.re_cipe.comments.ui.dto.CommentCreateRequest
import com.re_cipe.comments.ui.dto.CommentsResponse
import com.re_cipe.exception.BusinessException
import com.re_cipe.exception.ErrorCode
import com.re_cipe.member.domain.Member
import com.re_cipe.recipe.domain.repository.RecipeRepository
import com.re_cipe.recipe.domain.repository.ShortFormRecipeRepository
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Slice
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class CommentService(
    private val commentsRepository: CommentsRepository,
    private val commentLikesRepository: CommentLikesRepository,
    private val recipeRepository: RecipeRepository,
    private val shortFormCommentRepository: ShortFormCommentRepository,
    private val shortFormCommentLikesRepository: ShortFormCommentLikesRepository,
    private val shortFormRecipeRepository: ShortFormRecipeRepository
) {

    fun findAllRecipeComments(recipeId: Long, offset: Int, pageSize: Int, member: Member): Slice<CommentsResponse> {
        return commentsRepository.findCommentsByRecipeId(recipeId = recipeId, createPageable(offset, pageSize))
            .map { comment ->
                CommentsResponse.of(
                    comment = comment,
                    commentLikesRepository.existsByCommentsIdAndLikedById(comment.id, memberId = member.id)
                )
            }
    }

    @Transactional
    fun createOneComment(recipeId: Long, member: Member, commentCreateRequest: CommentCreateRequest): Long {
        val comments = Comments(
            content = commentCreateRequest.content,
            writtenBy = member,
            recipe = recipeRepository.findById(recipeId).orElseThrow { BusinessException(ErrorCode.NO_RECIPE_FOUND) }
        )
        return commentsRepository.save(comments).id
    }

    @Transactional
    fun deleteComment(commentId: Long, member: Member): Boolean {
        val comments = commentsRepository.findById(commentId).orElseThrow {
            BusinessException(ErrorCode.NO_COMMENT_FOUND)
        }
        if (comments.writtenBy.id != member.id) {
            throw BusinessException(ErrorCode.NO_AUTHENTICATION)
        }
        commentsRepository.deleteComment(commentId)
        return true
    }

    @Transactional
    fun likeComment(commentId: Long, member: Member): Boolean {
        val comment = commentsRepository.findById(commentId)
            .orElseThrow { BusinessException(ErrorCode.NO_COMMENT_FOUND) }
        if (commentLikesRepository.existsByCommentsIdAndLikedById(commentId, memberId = member.id)) {
            throw BusinessException(ErrorCode.ALREADY_LIKED_COMMENT)
        }
        val commentLikes = CommentLikes(likedBy = member, comments = comment)

        comment.likes.add(commentLikes)
        commentLikesRepository.save(commentLikes)
        return true
    }

    @Transactional
    fun unlikeComment(commentId: Long, member: Member): Boolean {
        val commentLikes =
            commentLikesRepository.findByCommentsIdAndLikedById(commentId, memberId = member.id)
        val comment = commentsRepository.findById(commentId)
            .orElseThrow { BusinessException(ErrorCode.NO_COMMENT_FOUND) }
        commentLikesRepository.delete(commentLikes)
        comment.likes.remove(commentLikes)
        return true
    }

    private fun createPageable(offset: Int, pageSize: Int): Pageable {
        return PageRequest.of(offset, pageSize, Sort.Direction.DESC, "createdAt")
    }

    fun findAllShortFormComments(
        shortformId: Long,
        offset: Int,
        pageSize: Int,
        member: Member
    ): Slice<CommentsResponse> {
        return shortFormCommentRepository.findCommentsByShortFormId(
            shortFormId = shortformId,
            createPageable(offset, pageSize)
        )
            .map { comment ->
                CommentsResponse.of(
                    comment = comment,
                    shortFormCommentLikesRepository.existsByLikedByIdAndCommentsId(
                        memberId = member.id,
                        shortformId = shortformId
                    )
                )
            }
    }

    @Transactional
    fun createOneShortFormComment(shortformId: Long, member: Member, commentCreateRequest: CommentCreateRequest): Long {
        val comments = ShortFormComments(
            content = commentCreateRequest.content,
            writtenBy = member,
            shortFormRecipe = shortFormRecipeRepository.findById(shortformId)
                .orElseThrow { BusinessException(ErrorCode.NO_RECIPE_FOUND) }
        )
        return shortFormCommentRepository.save(comments).id
    }

    @Transactional
    fun likeShortFormComment(commentId: Long, member: Member): Boolean {
        val comment = shortFormCommentRepository.findById(commentId)
            .orElseThrow { BusinessException(ErrorCode.NO_COMMENT_FOUND) }
        val commentLikes = ShortFormCommentsLikes(likedBy = member, comments = comment)
        comment.likes.add(commentLikes)
        shortFormCommentLikesRepository.save(commentLikes)
        return true
    }

    @Transactional
    fun unlikeShortFormComment(commentId: Long, member: Member): Boolean {
        val commentLikes =
            shortFormCommentLikesRepository.findByCommentsIdAndLikedById(
                memberId = member.id,
                commentId = commentId
            )
        val comment = shortFormCommentRepository.findById(commentId)
            .orElseThrow { BusinessException(ErrorCode.NO_COMMENT_FOUND) }
        shortFormCommentLikesRepository.delete(commentLikes)
        comment.likes.remove(commentLikes)
        return true
    }

    @Transactional
    fun deleteShortFormComment(commentId: Long, member: Member): Boolean {
        val comments = shortFormCommentRepository.findById(commentId).orElseThrow {
            BusinessException(ErrorCode.NO_COMMENT_FOUND)
        }
        if (comments.writtenBy.id != member.id) {
            throw BusinessException(ErrorCode.NO_AUTHENTICATION)
        }
        shortFormCommentRepository.deleteComment(commentId)
        return true
    }
}