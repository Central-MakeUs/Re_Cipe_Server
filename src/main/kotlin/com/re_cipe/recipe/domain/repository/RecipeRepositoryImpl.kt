package com.re_cipe.recipe.domain.repository

import com.querydsl.core.types.dsl.BooleanExpression
import com.querydsl.core.types.dsl.NumberExpression
import com.querydsl.jpa.impl.JPAQueryFactory
import com.re_cipe.ingredient.domain.IngredientType
import org.springframework.stereotype.Repository
import javax.persistence.EntityManager


import com.re_cipe.recipe.domain.QRecipe.recipe
import com.re_cipe.ingredient.domain.QRecipeIngredients.recipeIngredients
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
            .where(recipe.isDeleted.isFalse)
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
            .where(recipe.createdAt.between(oneWeekAgo, now).and(recipe.isDeleted.isFalse))
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
            .where(recipe.isDeleted.isFalse)
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
            .innerJoin(savedRecipe)
            .on(savedRecipe.recipe.id.eq(recipe.id))
            .where(savedRecipe.savedBy.id.eq(memberId).and(recipe.isDeleted.isFalse))
            .fetch()
    }

    override fun findUsersWrittenRecipe(memberId: Long): List<Recipe> {
        return queryFactory.selectFrom(recipe)
            .where(recipe.writtenBy.id.eq(memberId).and(recipe.isDeleted.isFalse))
            .fetch()
    }

    override fun deleteOneRecipe(recipeId: Long) {
        queryFactory.update(recipe)
            .where(recipe.id.eq(recipeId))
            .set(recipe.isDeleted, true)
            .execute()
    }

    override fun searchRecipeByKeyword(keyword: String, pageable: Pageable): Slice<Recipe> {
        val content = queryFactory
            .selectFrom(recipe)
            .where(
                recipe.isDeleted.isFalse.and(
                    containsKeywordInRecipe(keyword)
                )
            )
            .orderBy(recipe.createdAt.desc())
            .offset(pageable.offset)
            .limit(pageable.pageSize.toLong())
            .fetch()

        val pageRequest = PageRequest.of(pageable.pageNumber, pageable.pageSize)
        val hasNext = content.size > pageable.pageSize

        return SliceImpl(content, pageRequest, hasNext)
    }

    private fun containsKeywordInRecipe(keyword: String): BooleanExpression {
        return recipe.name.containsIgnoreCase(keyword)
            .or(recipe.description.containsIgnoreCase(keyword))
            .or(recipe.reviewsList.any().content.containsIgnoreCase(keyword))
            .or(recipe.ingredientList.any().ingredient.name.containsIgnoreCase(keyword))
    }

    override fun findRecipeByThemeLivingAlone(pageable: Pageable): Slice<Recipe> {
        val content = queryFactory
            .selectFrom(recipe)
            .innerJoin(recipeIngredients)
            .on(recipeIngredients.recipe.id.eq(recipe.id))
            .where(recipe.isDeleted.isFalse.and(recipeIngredients.ingredient.ingredientType.eq(IngredientType.SAUCE)))
            .orderBy(recipe.createdAt.desc())
            .offset(pageable.offset)
            .limit(pageable.pageSize.toLong())
            .fetch()

        val pageRequest = PageRequest.of(pageable.pageNumber, pageable.pageSize)
        val hasNext = content.size > pageable.pageSize

        return SliceImpl(content, pageRequest, hasNext)
    }

    override fun findRecipeByThemeForDieting(pageable: Pageable): Slice<Recipe> {
        val content = queryFactory
            .selectFrom(recipe)
            .innerJoin(recipeIngredients)
            .on(recipeIngredients.recipe.id.eq(recipe.id))
            .where(
                recipe.isDeleted.isFalse.and(
                    recipeIngredients.ingredient.ingredientType.eq(IngredientType.VEGETABLE)
                        .or(recipeIngredients.ingredient.ingredientType.eq(IngredientType.DAIRY))
                )
            )
            .orderBy(recipe.createdAt.desc())
            .offset(pageable.offset)
            .limit(pageable.pageSize.toLong())
            .fetch()

        val pageRequest = PageRequest.of(pageable.pageNumber, pageable.pageSize)
        val hasNext = content.size > pageable.pageSize

        return SliceImpl(content, pageRequest, hasNext)
    }

    override fun findRecipeByThemeBudgetHappiness(pageable: Pageable): Slice<Recipe> {
        val content = queryFactory
            .selectFrom(recipe)
            .innerJoin(recipeIngredients)
            .on(recipeIngredients.recipe.id.eq(recipe.id))
            .where(
                recipe.isDeleted.isFalse.and(
                    recipeIngredients.ingredient.ingredientType.eq(IngredientType.VEGETABLE)
                )
            )
            .orderBy(recipe.createdAt.desc())
            .offset(pageable.offset)
            .limit(pageable.pageSize.toLong())
            .fetch()

        val pageRequest = PageRequest.of(pageable.pageNumber, pageable.pageSize)
        val hasNext = content.size > pageable.pageSize

        return SliceImpl(content, pageRequest, hasNext)
    }

    override fun findRecipeByThemeHousewarming(pageable: Pageable): Slice<Recipe> {
        val content = queryFactory
            .selectFrom(recipe)
            .innerJoin(recipeIngredients)
            .on(recipeIngredients.recipe.id.eq(recipe.id))
            .where(
                recipe.isDeleted.isFalse.and(
                    recipeIngredients.ingredient.ingredientType.eq(IngredientType.MEAT)
                        .or(recipeIngredients.ingredient.ingredientType.eq(IngredientType.SEAFOOD))
                )
            )
            .orderBy(recipe.createdAt.desc())
            .offset(pageable.offset)
            .limit(pageable.pageSize.toLong())
            .fetch()

        val pageRequest = PageRequest.of(pageable.pageNumber, pageable.pageSize)
        val hasNext = content.size > pageable.pageSize

        return SliceImpl(content, pageRequest, hasNext)
    }

    override fun recommendRecipe(recipeId: Long): List<Recipe> {
        return queryFactory.selectFrom(recipe)
            .where(recipe.id.ne(recipeId))
            .orderBy(NumberExpression.random().asc())
            .limit(3)
            .fetch()
    }

}