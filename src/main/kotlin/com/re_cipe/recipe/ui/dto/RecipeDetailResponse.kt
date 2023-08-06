package com.re_cipe.recipe.ui.dto

import com.re_cipe.ingredient.ui.dto.IngredientResponse
import com.re_cipe.stage.ui.dto.StageResponse

data class RecipeDetailResponse(
    val recipe_id: Long,
    val recipe_name: String,
    val recipe_description: String,
    val cook_time: Int,
    val serving_size: Int,
    val writtenby: String,
    val stages: MutableList<StageResponse>,
    val ingredients: MutableList<IngredientResponse>
)