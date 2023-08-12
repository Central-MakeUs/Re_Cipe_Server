package com.re_cipe.recipe.ui.dto

import com.re_cipe.ingredient.ui.dto.IngredientRecipeRequest

data class RecipeCreateRequest(
    val recipe_name: String,
    val recipe_description: String,
    val recipe_thumbnail_img: String,
    val ingredients: MutableList<IngredientRecipeRequest>,
    val cook_time: Int,
    val serving_size: Int,
    val recipe_stages: MutableList<RecipeStageRequest>
)