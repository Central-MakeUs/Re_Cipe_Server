package com.re_cipe.stage.domain.repository

import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Repository
import javax.persistence.EntityManager


import com.re_cipe.stage.domain.QRecipeStage.recipeStage
import com.re_cipe.stage.domain.RecipeStage

@Repository
class StageRepositoryImpl(entityManager: EntityManager) : StageRepositoryCustom {
    private val queryFactory: JPAQueryFactory = JPAQueryFactory(entityManager)

    override fun findAllRecipeStages(recipeId: Long): List<RecipeStage> {
        return queryFactory.selectFrom(recipeStage)
            .where(recipeStage.recipe.id.eq(recipeId))
            .fetch()
    }
}