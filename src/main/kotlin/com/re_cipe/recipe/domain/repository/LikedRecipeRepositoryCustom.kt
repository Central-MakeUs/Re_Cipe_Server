package com.re_cipe.recipe.domain.repository

import com.re_cipe.recipe.domain.LikedRecipe
import org.springframework.data.jpa.repository.JpaRepository

interface LikedRecipeRepositoryCustom {
    fun unlikeRecipeAndMember(memberId: Long, recipeId: Long): Boolean
}