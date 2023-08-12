package com.re_cipe.ingredient.ui.dto

import com.re_cipe.ingredient.domain.Ingredient
import com.re_cipe.ingredient.domain.IngredientType.Companion.getIngredientType
import com.re_cipe.ingredient.domain.UnitEnum

data class IngredientSimpleResponse(
    val ingredient_id: Long,
    val ingredient_name: String,
    val ingredient_type: String,
    val ingredient_unit: UnitEnum
) {
    companion object {
        fun of(ingredient: Ingredient): IngredientSimpleResponse {
            return IngredientSimpleResponse(
                ingredient_id = ingredient.id,
                ingredient_name = ingredient.name,
                ingredient_type = getIngredientType(ingredient.ingredientType),
                ingredient_unit = ingredient.ingredientUnitEnum
            )
        }
    }
}