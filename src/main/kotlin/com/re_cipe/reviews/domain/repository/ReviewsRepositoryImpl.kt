package com.re_cipe.reviews.domain.repository

import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Repository
import javax.persistence.EntityManager

import com.re_cipe.reviews.domain.QReviews.reviews
import com.re_cipe.reviews.domain.Reviews
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Slice
import org.springframework.data.domain.SliceImpl

@Repository
class ReviewsRepositoryImpl(entityManager: EntityManager) : ReviewsRepositoryCustom {
    private val queryFactory: JPAQueryFactory = JPAQueryFactory(entityManager)

    override fun findReviewsByLatest(pageable: Pageable, recipeId: Long): Slice<Reviews> {
        val query = queryFactory.selectFrom(reviews)
            .where(reviews.recipe.id.eq(recipeId))
            .orderBy(reviews.modifiedAt.asc())

        val pageRequest = PageRequest.of(pageable.pageNumber, pageable.pageSize)
        query.offset(pageRequest.offset)
        query.limit(pageRequest.pageSize.toLong())

        val content = query.fetch()
        val hasNext = content.size > pageRequest.pageSize

        return SliceImpl(content, pageRequest, hasNext)
    }

    override fun findReviewsByPopular(pageable: Pageable, recipeId: Long): Slice<Reviews> {
        TODO("Not yet implemented")
    }
}