package com.re_cipe.recipe.ui.dto

import com.re_cipe.recipe.domain.Recipe

data class RecipeRecommendResponse(
    val img_url: String,
    val recipe_name: String,
    val cooking_time: Int
) {
    companion object {
        fun of(recipe: Recipe): RecipeRecommendResponse {
            return RecipeRecommendResponse(
                img_url = recipe.thumbnail_img,
                recipe_name = recipe.name,
                cooking_time = recipe.cook_time,
            )
        }
    }
}