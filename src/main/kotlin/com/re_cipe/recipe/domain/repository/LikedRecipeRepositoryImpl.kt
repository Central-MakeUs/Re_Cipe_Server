package com.re_cipe.recipe.domain.repository

import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Repository
import javax.persistence.EntityManager


import com.re_cipe.recipe.domain.QLikedRecipe.likedRecipe

@Repository
class LikedRecipeRepositoryImpl(entityManager: EntityManager) : LikedRecipeRepositoryCustom {
    private val queryFactory: JPAQueryFactory = JPAQueryFactory(entityManager)

    override fun unlikeRecipeAndMember(memberId: Long, recipeId: Long): Boolean {
        queryFactory.delete(likedRecipe)
            .where(likedRecipe.recipe.id.eq(recipeId).and(likedRecipe.likedBy.id.eq(memberId)))
            .execute()
        return true
    }
}