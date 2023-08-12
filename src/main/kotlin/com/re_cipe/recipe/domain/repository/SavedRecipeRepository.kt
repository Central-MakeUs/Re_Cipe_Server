package com.re_cipe.recipe.domain.repository

import com.re_cipe.recipe.domain.Recipe
import org.springframework.data.jpa.repository.JpaRepository

interface SavedRecipeRepository: JpaRepository<Recipe, Long>, SavedRecipeRepositoryCustom {
}