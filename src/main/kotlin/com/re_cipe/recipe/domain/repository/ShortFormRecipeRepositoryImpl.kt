package com.re_cipe.recipe.domain.repository

import com.querydsl.jpa.impl.JPAQueryFactory
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
            .offset(pageable.offset)
            .limit(pageable.pageSize.toLong())
            .fetch()

        val pageRequest = PageRequest.of(pageable.pageNumber, pageable.pageSize)
        val hasNext = content.size > pageable.pageSize

        return SliceImpl(content, pageRequest, hasNext)
    }
}