package com.re_cipe.comments.domain.repository

import com.re_cipe.comments.domain.QShortFormComments.shortFormComments
import com.re_cipe.recipe.domain.QShortFormRecipe.shortFormRecipe
import com.querydsl.jpa.impl.JPAQueryFactory
import com.re_cipe.comments.domain.ShortFormComments
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Slice
import org.springframework.data.domain.SliceImpl
import org.springframework.stereotype.Repository
import javax.persistence.EntityManager

@Repository
class ShortFormCommentRepositoryImpl(entityManager: EntityManager) : ShortFormCommentRepositoryCustom {
    private val queryFactory: JPAQueryFactory = JPAQueryFactory(entityManager)

    override fun findCommentsByShortFormId(shortFormId: Long, pageable: Pageable): Slice<ShortFormComments> {
        val content = queryFactory
            .selectFrom(shortFormComments)
            .innerJoin(shortFormRecipe)
            .on(shortFormComments.shortFormRecipe.id.eq(shortFormRecipe.id))
            .where(shortFormRecipe.id.eq(shortFormId).and(shortFormComments.isDeleted.isFalse))
            .offset(pageable.offset)
            .limit(pageable.pageSize.toLong())
            .fetch()

        val pageRequest = PageRequest.of(pageable.pageNumber, pageable.pageSize)
        val hasNext = content.size > pageable.pageSize

        return SliceImpl(content, pageRequest, hasNext)
    }

    override fun deleteComment(commendId: Long): Boolean {
        queryFactory.update(shortFormComments)
            .where(shortFormComments.id.eq(commendId))
            .set(shortFormComments.isDeleted, true)
            .execute()
        return true
    }
}