package com.re_cipe.recipe.domain.repository

import com.re_cipe.recipe.domain.Recipe
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Slice

interface RecipeRepositoryCustom {
    fun findRecipesByLatest(pageable: Pageable): Slice<Recipe>
    fun findRecipesByPopular(pageable: Pageable): Slice<Recipe>
    fun findRecipesByShortestTime(pageable: Pageable): Slice<Recipe>
    fun findUserSavedRecipes(memberId: Long): List<Recipe>
    fun findUsersWrittenRecipe(memberId: Long): List<Recipe>
    fun deleteOneRecipe(recipeId: Long)
}