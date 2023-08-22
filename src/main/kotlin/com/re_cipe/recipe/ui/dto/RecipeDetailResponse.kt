package com.re_cipe.recipe.ui.dto

import com.re_cipe.ingredient.ui.dto.IngredientResponse
import com.re_cipe.recipe.domain.Recipe
import com.re_cipe.stage.ui.dto.StageResponse
import java.time.LocalDateTime

data class RecipeDetailResponse(
    val recipe_id: Long,
    val recipe_name: String,
    val recipe_description: String,
    val cook_time: Int,
    val serving_size: Int,
    val writtenby: String,
    val rating: Double,
    val created_date: LocalDateTime,
    val recipe_thumbnail_img: String?,
    val ingredients: List<IngredientResponse>,
    val stages: List<StageResponse>,
    val is_saved: Boolean,
    val recommendation_recipes: List<RecipeRecommendResponse>
) {
    companion object {
        fun of(
            recipe: Recipe,
            stages: List<StageResponse>,
            is_saved: Boolean,
            recommends: List<Recipe>
        ): RecipeDetailResponse {
            return RecipeDetailResponse(
                recipe_id = recipe.id,
                recipe_name = recipe.name,
                recipe_description = recipe.description,
                cook_time = recipe.cook_time,
                serving_size = recipe.serving_size,
                writtenby = recipe.writtenBy.nickname,
                rating = recipe.rating,
                created_date = recipe.createdAt,
                recipe_thumbnail_img = recipe.thumbnail_img,
                ingredients = recipe.ingredientList.map { recipeIngredients ->
                    IngredientResponse.of(
                        recipeIngredients.ingredient,
                        recipeIngredients.ingredientSize
                    )
                },
                stages = stages,
                is_saved = is_saved,
                recommendation_recipes = recommends.map { r -> RecipeRecommendResponse.of(r) }
            )
        }
    }
}