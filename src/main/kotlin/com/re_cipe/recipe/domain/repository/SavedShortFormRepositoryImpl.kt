package com.re_cipe.recipe.domain.repository

import com.querydsl.jpa.impl.JPAQueryFactory
import com.re_cipe.member.domain.Member
import org.springframework.stereotype.Repository
import javax.persistence.EntityManager

@Repository
class SavedShortFormRepositoryImpl(entityManager: EntityManager) : SavedShortFormRepositoryCustom {
    private val queryFactory: JPAQueryFactory = JPAQueryFactory(entityManager)

    override fun unsaveShortFormRecipe(member: Member, shortFormRecipeId: Long): Boolean {
        TODO("Not yet implemented")
        return true
    }
}
