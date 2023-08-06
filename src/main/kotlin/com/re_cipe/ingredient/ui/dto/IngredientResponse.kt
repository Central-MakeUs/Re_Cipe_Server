package com.re_cipe.ingredient.ui.dto

import com.re_cipe.ingredient.domain.IngredientType

data class IngredientResponse(
    val ingredient_name: String,
    val ingredient_type: IngredientType,
    val ingredient_unit: Unit
)