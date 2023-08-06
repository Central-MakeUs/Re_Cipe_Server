package com.re_cipe.recipe.service

import com.re_cipe.recipe.domain.Recipe
import com.re_cipe.recipe.domain.repository.RecipeRepository
import com.re_cipe.recipe.ui.dto.RecipeResponse
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Slice
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class RecipeService(
    val recipeRepository: RecipeRepository
) {
    fun getRecipesByLatest(pageable: Pageable): Slice<RecipeResponse> {
        return recipeRepository.findRecipesByLatest(pageable).map { recipe -> RecipeResponse.of(recipe) }
    }

    fun getRecipesByPopular(pageable: Pageable): Slice<RecipeResponse> {
        return recipeRepository.findRecipesByPopular(pageable).map { recipe -> RecipeResponse.of(recipe) }
    }

    fun getRecipesByShortestTime(pageable: Pageable): Slice<RecipeResponse> {
        return recipeRepository.findRecipesByShortestTime(pageable).map { recipe -> RecipeResponse.of(recipe) }
    }

    fun findUserSavedRecipe(memberId: Long): List<RecipeResponse> {
        return recipeRepository.findUserSavedRecipes(memberId).map { recipe: Recipe -> RecipeResponse.of(recipe) }
    }

    fun findMyRecipes(memberId: Long): List<RecipeResponse> {
        return recipeRepository.findUsersWrittenRecipe(memberId).map { recipe: Recipe -> RecipeResponse.of(recipe) }
    }

    @Transactional
    fun deleteMyRecipe(memberId: Long, recipeId: Long): Boolean {
        recipeRepository.deleteOneRecipe(memberId, recipeId)
        return true
    }
}