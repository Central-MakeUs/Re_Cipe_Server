package com.re_cipe.recipe.domain.repository

import com.querydsl.core.types.dsl.BooleanExpression
import com.querydsl.jpa.impl.JPAQueryFactory
import com.re_cipe.recipe.domain.QRecipe
import org.springframework.stereotype.Repository
import javax.persistence.EntityManager


import com.re_cipe.recipe.domain.QShortFormRecipe.shortFormRecipe
import com.re_cipe.recipe.domain.ShortFormRecipe
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Slice
import org.springframework.data.domain.SliceImpl

@Repository
class ShortFormRecipeRepositoryImpl(entityManager: EntityManager) : ShortFormRecipeRepositoryCustom {
    private val queryFactory: JPAQueryFactory = JPAQueryFactory(entityManager)

    override fun findShortFormRecipes(pageable: Pageable): Slice<ShortFormRecipe> {
        val content = queryFactory
            .selectFrom(shortFormRecipe)
            .where(shortFormRecipe.isDeleted.isFalse)
            .offset(pageable.offset)
            .limit(pageable.pageSize.toLong())
            .fetch()

        val pageRequest = PageRequest.of(pageable.pageNumber, pageable.pageSize)
        val hasNext = content.size > pageable.pageSize

        return SliceImpl(content, pageRequest, hasNext)
    }

    override fun deleteShortFormRecipe(shortFormId: Long): Boolean {
        queryFactory.update(shortFormRecipe)
            .where(shortFormRecipe.id.eq(shortFormId))
            .set(shortFormRecipe.isDeleted, true)
            .execute()
        return true
    }

    override fun searchRecipeByKeyword(keyword: String, pageable: Pageable): Slice<ShortFormRecipe> {
        val content = queryFactory
            .selectFrom(shortFormRecipe)
            .where(
                shortFormRecipe.isDeleted.isFalse.and(
                    containsKeywordInRecipe(keyword)
                )
            )
            .orderBy(shortFormRecipe.createdAt.desc())
            .offset(pageable.offset)
            .limit(pageable.pageSize.toLong())
            .fetch()

        val pageRequest = PageRequest.of(pageable.pageNumber, pageable.pageSize)
        val hasNext = content.size > pageable.pageSize

        return SliceImpl(content, pageRequest, hasNext)
    }

    private fun containsKeywordInRecipe(keyword: String): BooleanExpression {
        return shortFormRecipe.name.containsIgnoreCase(keyword)
            .or(shortFormRecipe.description.containsIgnoreCase(keyword))
            .or(shortFormRecipe.commentList.any().content.containsIgnoreCase(keyword))
            .or(shortFormRecipe.ingredients.any().ingredient.name.containsIgnoreCase(keyword))
    }
}