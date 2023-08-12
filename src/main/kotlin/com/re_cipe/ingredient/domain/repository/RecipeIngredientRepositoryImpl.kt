package com.re_cipe.ingredient.domain.repository

import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Repository
import javax.persistence.EntityManager

@Repository
class RecipeIngredientRepositoryImpl(entityManager: EntityManager) : RecipeIngredientRepositoryCustom {
    private val queryFactory: JPAQueryFactory = JPAQueryFactory(entityManager)

}