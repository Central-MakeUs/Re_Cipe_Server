package com.re_cipe.recipe.ui.dto

import com.re_cipe.recipe.domain.Recipe
import java.time.LocalDateTime

data class RecipeResponse(
    val recipe_id: Long,
    val recipe_name: String,
    val created_date: LocalDateTime,
    val nickname: String,
    val writtenid: Long,
    val rating: Double,
    val comment_count: Int,
    val recipe_thumbnail_img: String,
    val is_saved: Boolean = false,
    val cook_time: Int
) {
    companion object {
        fun of(recipe: Recipe, is_saved: Boolean): RecipeResponse {
            return RecipeResponse(
                recipe_id = recipe.id,
                recipe_name = recipe.name,
                created_date = recipe.createdAt,
                nickname = recipe.writtenBy.nickname,
                writtenid = recipe.writtenBy.id,
                rating = recipe.rating,
                comment_count = recipe.comments.size,
                recipe_thumbnail_img = recipe.thumbnail_img,
                is_saved = is_saved,
                cook_time = recipe.cook_time
            )
        }
    }
}