package com.re_cipe.ingredient.domain.repository

import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Repository
import javax.persistence.EntityManager

@Repository
class ShortFormIngredientsRepositoryImpl(entityManager: EntityManager) : ShortFormIngredientsRepositoryCustom {
    private val queryFactory: JPAQueryFactory = JPAQueryFactory(entityManager)

}