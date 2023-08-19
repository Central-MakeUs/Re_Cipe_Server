package com.re_cipe.recipe.domain.repository

import com.re_cipe.recipe.domain.LikedRecipe
import org.springframework.data.jpa.repository.JpaRepository

interface LikedRecipeRepository : JpaRepository<LikedRecipe, Long>, LikedRecipeRepositoryCustom {
    fun existsByLikedByIdAndRecipeId(memberId: Long, recipeId: Long): Boolean
}