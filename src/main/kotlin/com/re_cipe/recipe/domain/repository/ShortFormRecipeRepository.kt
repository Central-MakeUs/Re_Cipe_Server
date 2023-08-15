package com.re_cipe.recipe.domain.repository

import com.re_cipe.recipe.domain.ShortFormRecipe
import org.springframework.data.jpa.repository.JpaRepository

interface ShortFormRecipeRepository : JpaRepository<ShortFormRecipe, Long>, ShortFormRecipeRepositoryCustom {
}