package com.re_cipe.comments.domain.repository

import com.querydsl.jpa.impl.JPAQueryFactory
import com.re_cipe.comments.domain.Comments
import com.re_cipe.comments.domain.QComments.comments
import com.re_cipe.recipe.domain.QRecipe.recipe
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Slice
import org.springframework.data.domain.SliceImpl
import org.springframework.stereotype.Repository
import javax.persistence.EntityManager

@Repository
class CommentsRepositoryImpl(entityManager: EntityManager) : CommentsRepositoryCustom {
    private val queryFactory: JPAQueryFactory = JPAQueryFactory(entityManager)

    override fun findCommentsByRecipeId(recipeId: Long, pageable: Pageable): Slice<Comments> {
        val content = queryFactory
            .selectFrom(comments)
            .innerJoin(recipe)
            .on(comments.recipe.id.eq(recipe.id))
            .where(recipe.id.eq(recipeId).and(comments.isDeleted.isFalse))
            .offset(pageable.offset)
            .limit(pageable.pageSize.toLong())
            .fetch()

        val pageRequest = PageRequest.of(pageable.pageNumber, pageable.pageSize)
        val hasNext = content.size > pageable.pageSize

        return SliceImpl(content, pageRequest, hasNext)
    }

    override fun deleteComment(commentId: Long): Boolean {
        queryFactory.update(comments)
            .where(comments.id.eq(commentId))
            .set(comments.isDeleted, true)
            .execute()
        return true
    }
}