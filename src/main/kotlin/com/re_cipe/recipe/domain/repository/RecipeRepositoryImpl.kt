package com.re_cipe.recipe.domain.repository

import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Repository
import javax.persistence.EntityManager


import com.re_cipe.recipe.domain.QRecipe.recipe
import com.re_cipe.recipe.domain.Recipe
import com.re_cipe.recipe.domain.QSavedRecipe.savedRecipe
import com.re_cipe.member.domain.QMember.member
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Slice
import org.springframework.data.domain.SliceImpl

@Repository
class RecipeRepositoryImpl(entityManager: EntityManager) : RecipeRepositoryCustom {
    private val queryFactory: JPAQueryFactory = JPAQueryFactory(entityManager)

    override fun findRecipesByLatest(pageable: Pageable): Slice<Recipe> {
        val query = queryFactory
            .selectFrom(recipe)
            .orderBy(recipe.createdAt.desc())

        val pageRequest = PageRequest.of(pageable.pageNumber, pageable.pageSize)
        query.offset(pageRequest.offset)
        query.limit(pageRequest.pageSize.toLong())

        val content = query.fetch()
        val hasNext = content.size > pageRequest.pageSize

        return SliceImpl(content, pageRequest, hasNext)
    }

    override fun findRecipesByPopular(pageable: Pageable): Slice<Recipe> {
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

    override fun findRecipesByShortestTime(pageable: Pageable): Slice<Recipe> {
        val query = queryFactory
            .selectFrom(recipe)
            .orderBy(recipe.cook_time.asc()) // 'cook_time'은 레시피의 요리 시간을 나타내는 필드라고 가정

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

    override fun deleteOneRecipe(memberId: Long, recipeId: Long) {
        queryFactory.delete(recipe)
            .where(recipe.id.eq(recipeId).and(member.id.eq(memberId)))
    }
}