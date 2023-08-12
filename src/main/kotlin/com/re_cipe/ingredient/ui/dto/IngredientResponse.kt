package com.re_cipe.ingredient.ui.dto

import com.re_cipe.ingredient.domain.Ingredient
import com.re_cipe.ingredient.domain.IngredientType.Companion.getIngredientType
import com.re_cipe.ingredient.domain.UnitEnum

data class IngredientResponse(
    val ingredient_id: Long,
    val ingredient_name: String,
    val ingredient_type: String,
    val ingredient_size: Double,
    val ingredient_unit: UnitEnum,
    val coupang_url: String
) {
    companion object {
        fun of(ingredient: Ingredient, size: Double): IngredientResponse {
            return IngredientResponse(
                ingredient_id = ingredient.id,
                ingredient_name = ingredient.name,
                ingredient_type = getIngredientType(ingredient.ingredientType),
                ingredient_size = size,
                ingredient_unit = ingredient.ingredientUnitEnum,
                coupang_url = ingredient.coupangUrl
            )
        }
    }
}