package com.re_cipe.recipe.ui.dto

import com.re_cipe.ingredient.ui.dto.IngredientResponse
import com.re_cipe.recipe.domain.ShortFormRecipe
import java.time.LocalDateTime

data class ShortFormDetailResponse(
    val shortform_recipe_id: Long,
    val shortform_recipe_name: String,
    val shortform_recipe_description: String,
    val video_time: String,
    val writtenby: String,
    val writtenid: Long,
    val created_date: LocalDateTime,
    val video_url: String,
    val ingredients: List<IngredientResponse>,
    val liked_count: Int,
    val comments_count: Int,
    val saved_count: Int,
    val is_saved: Boolean,
    val is_liked: Boolean
) {
    companion object {
        fun of(recipe: ShortFormRecipe, is_saved: Boolean, is_liked: Boolean): ShortFormDetailResponse {
            return ShortFormDetailResponse(
                shortform_recipe_id = recipe.id,
                shortform_recipe_name = recipe.name,
                shortform_recipe_description = recipe.description,
                video_time = recipe.video_time,
                writtenby = recipe.writtenBy.nickname,
                writtenid = recipe.writtenBy.id,
                created_date = recipe.createdAt,
                ingredients = recipe.ingredients.map { shortformIngredients ->
                    IngredientResponse.of(
                        shortformIngredients.ingredient
                    )
                },
                video_url = recipe.video_url,
                liked_count = recipe.likedList.size,
                comments_count = recipe.commentList.size,
                saved_count = recipe.savedList.size,
                is_saved = is_saved,
                is_liked = is_liked
            )
        }
    }
}