package com.re_cipe.recipe.domain.repository

import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Repository
import javax.persistence.EntityManager


import com.re_cipe.recipe.domain.QSavedRecipe.savedRecipe

@Repository
class SavedRecipeRepositoryImpl(entityManager: EntityManager) : SavedRecipeRepositoryCustom {
    private val queryFactory: JPAQueryFactory = JPAQueryFactory(entityManager)

    override fun checkMemberSavedRecipe(recipeId: Long, memberId: Long): Boolean {
        return queryFactory.selectFrom(savedRecipe)
            .where(savedRecipe.recipe.id.eq(recipeId).and(savedRecipe.writtenBy.id.eq(memberId)))
            .fetch().size > 0
    }
}