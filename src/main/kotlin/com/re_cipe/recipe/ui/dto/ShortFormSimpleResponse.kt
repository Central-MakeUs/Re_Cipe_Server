package com.re_cipe.recipe.ui.dto

import com.re_cipe.ingredient.ui.dto.IngredientResponse
import com.re_cipe.recipe.domain.ShortFormRecipe
import java.time.LocalDateTime

data class ShortFormSimpleResponse(
    val shortform_id: Long,
    val video_url: String,
    val video_time: String,
    val shortform_name: String,
    val shortform_description: String,
    val likes_count: Int,
    val comments_count: Int,
    val saved_count: Int,
    val writtenBy: String,
    val is_saved: Boolean,
    val is_liked: Boolean,
    val ingredients: List<IngredientResponse>,
    val created_date: LocalDateTime,
) {
    companion object {
        fun of(recipe: ShortFormRecipe, is_liked: Boolean, is_saved: Boolean): ShortFormSimpleResponse {
            return ShortFormSimpleResponse(
                shortform_id = recipe.id,
                video_url = recipe.video_url,
                video_time = recipe.video_time,
                shortform_name = recipe.name,
                shortform_description = recipe.description,
                likes_count = recipe.likedList.size,
                comments_count = recipe.commentList.size,
                saved_count = recipe.savedList.size,
                writtenBy = recipe.writtenBy.nickname,
                is_saved = is_saved,
                is_liked = is_liked,
                created_date = recipe.createdAt,
                ingredients = recipe.ingredients.map { shortformIngredients ->
                    IngredientResponse.of(
                        shortformIngredients.ingredient
                    )
                },

            )
        }
    }
}