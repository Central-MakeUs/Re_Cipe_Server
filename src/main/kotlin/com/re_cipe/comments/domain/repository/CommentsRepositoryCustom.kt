package com.re_cipe.comments.domain.repository

import com.re_cipe.comments.domain.Comments
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Slice

interface CommentsRepositoryCustom {
    fun findCommentsByRecipeId(recipeId: Long, pageable: Pageable): Slice<Comments>
    fun deleteComment(commentId: Long): Boolean
}