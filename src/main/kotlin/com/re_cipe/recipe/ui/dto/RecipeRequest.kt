package com.re_cipe.recipe.ui.dto

data class RecipeRequest(
    val recipe_name: String,
    val recipe_description: String,
    val ingredients: MutableList<String>,
    val cook_time: Int,
    val serving_size: Int,
    val recipe_stages: MutableList<RecipeStageRequest>
)