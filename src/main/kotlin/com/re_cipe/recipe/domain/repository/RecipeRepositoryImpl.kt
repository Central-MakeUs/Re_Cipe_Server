package com.re_cipe.recipe.domain.repository

import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Repository
import javax.persistence.EntityManager


import com.re_cipe.recipe.domain.QRecipe.recipe
import com.re_cipe.recipe.domain.Recipe
import com.re_cipe.recipe.domain.QSavedRecipe.savedRecipe
import org.springframework.data.domain.*
import java.time.LocalDateTime

@Repository
class RecipeRepositoryImpl(entityManager: EntityManager) : RecipeRepositoryCustom {
    private val queryFactory: JPAQueryFactory = JPAQueryFactory(entityManager)

    override fun findRecipesByLatest(pageable: Pageable): Slice<Recipe> {
        val content = queryFactory
            .selectFrom(recipe)
            .orderBy(recipe.createdAt.desc())
            .offset(pageable.offset)
            .limit(pageable.pageSize.toLong())
            .fetch()

        val pageRequest = PageRequest.of(pageable.pageNumber, pageable.pageSize)
        val hasNext = content.size > pageable.pageSize

        return SliceImpl(content, pageRequest, hasNext)
    }

    override fun findRecipesByPopular(pageable: Pageable): Slice<Recipe> {
        val now = LocalDateTime.now()
        val oneWeekAgo = now.minusWeeks(1)

        val query = queryFactory
            .selectDistinct(recipe)
            .from(recipe)
            .where(recipe.createdAt.between(oneWeekAgo, now))
            .orderBy(recipe.comments.size().add(recipe.reviewsList.size()).desc())

        val pageRequest = PageRequest.of(pageable.pageNumber, pageable.pageSize)
        val content = query
            .offset(pageable.offset)
            .limit(pageable.pageSize.toLong())
            .fetch()

        val hasNext = content.size > pageable.pageSize

        return SliceImpl(content, pageRequest, hasNext)
    }

    override fun findRecipesByShortestTime(pageable: Pageable): Slice<Recipe> {
        val query = queryFactory
            .selectFrom(recipe)
            .orderBy(recipe.cook_time.asc())

        val pageRequest = PageRequest.of(pageable.pageNumber, pageable.pageSize)
        query.offset(pageRequest.offset)
        query.limit(pageRequest.pageSize.toLong())

        val content = query.fetch()
        val hasNext = content.size > pageRequest.pageSize

        return SliceImpl(content, pageRequest, hasNext)
    }

    override fun findUserSavedRecipes(memberId: Long): List<Recipe> {
        return queryFactory.selectFrom(recipe)
            .join(savedRecipe)
            .on(savedRecipe.writtenBy.id.eq(memberId))
            .fetch()
    }

    override fun findUsersWrittenRecipe(memberId: Long): List<Recipe> {
        return queryFactory.selectFrom(recipe)
            .where(recipe.writtenBy.id.eq(memberId))
            .fetch()
    }

    override fun deleteOneRecipe(recipeId: Long) {
        queryFactory.delete(recipe)
            .where(recipe.id.eq(recipeId))
            .execute()
    }
}