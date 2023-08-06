package com.re_cipe.recipe.ui.dto

import com.re_cipe.recipe.domain.Recipe
import java.time.LocalDateTime

data class RecipeResponse(
    val recipe_id: Long,
    val image_url: String?,
    val recipe_name: String,
    val created_date: LocalDateTime,
    val nickname: String,
    val rating: Int,
    val comment_count: Int,
    val is_saved: Boolean = false
) {
    companion object {
        fun of(recipe: Recipe): RecipeResponse {
            return RecipeResponse(
                recipe_id = recipe.id,
                image_url = recipe.stages[0].image_url,
                recipe_name = recipe.name,
                created_date = recipe.createdAt,
                nickname = recipe.writtenBy.nickname,
                rating = recipe.rating,
                comment_count = recipe.comments.size,
                is_saved = true
            )
        }
    }
}