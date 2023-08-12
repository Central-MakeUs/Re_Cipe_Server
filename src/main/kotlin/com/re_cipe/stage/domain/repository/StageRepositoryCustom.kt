package com.re_cipe.stage.domain.repository

import com.re_cipe.stage.domain.RecipeStage

interface StageRepositoryCustom {
    fun findAllRecipeStages(recipeId: Long): List<RecipeStage>
}