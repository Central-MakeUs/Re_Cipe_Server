package com.re_cipe.recipe.domain.repository

import com.querydsl.jpa.impl.JPAQueryFactory
import com.re_cipe.member.domain.Member
import com.re_cipe.recipe.domain.LikedShortFormRecipe
import com.re_cipe.recipe.domain.SavedShortFormRecipe
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import javax.persistence.EntityManager

@Repository
class LikedShortFormRepositoryImpl(entityManager: EntityManager) : LikedShortFormRepositoryCustom {
    private val queryFactory: JPAQueryFactory = JPAQueryFactory(entityManager)

    override fun unlikeShortFormRecipeAndMember(memberId: Long, shortFormRecipeId: Long): Boolean {
        TODO("Not yet implemented")
        return true
    }
}
