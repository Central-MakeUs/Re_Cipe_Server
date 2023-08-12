package com.re_cipe.recipe.domain.repository

import com.re_cipe.recipe.domain.Recipe
import com.re_cipe.recipe.domain.SavedRecipe
import org.springframework.data.jpa.repository.JpaRepository

interface SavedRecipeRepository : JpaRepository<SavedRecipe, Long>, SavedRecipeRepositoryCustom {
}