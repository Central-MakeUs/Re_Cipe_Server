package com.re_cipe.ingredient.ui.dto

data class IngredientRequest (
    val name: String,
    val ingredientType: String,
    val ingredientUnitEnum: String,
    val coupangUrl: String,
    val coupangProductImage: String,
    val coupangProductName: String,
    val coupangProductPrice: Int,
    val isRocketDelivery: Boolean
)