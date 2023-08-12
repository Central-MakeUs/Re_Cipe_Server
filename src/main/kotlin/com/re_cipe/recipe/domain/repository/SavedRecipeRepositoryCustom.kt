package com.re_cipe.recipe.domain.repository

import com.re_cipe.recipe.domain.Recipe
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Slice

interface SavedRecipeRepositoryCustom {
    fun checkMemberSavedRecipe(recipeId: Long, memberId: Long): Boolean
}