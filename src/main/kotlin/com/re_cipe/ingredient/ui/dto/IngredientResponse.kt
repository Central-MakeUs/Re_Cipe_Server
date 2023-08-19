package com.re_cipe.ingredient.ui.dto

import com.re_cipe.ingredient.domain.Ingredient
import com.re_cipe.ingredient.domain.IngredientType.Companion.getIngredientType
import com.re_cipe.ingredient.domain.UnitEnum

data class IngredientResponse(
    val ingredient_id: Long,
    val ingredient_name: String,
    val ingredient_type: String,
    val ingredient_size: Double?,
    val ingredient_unit: UnitEnum,
    val coupang_product_image: String,
    val coupang_product_name: String,
    val coupang_product_price: Int,
    val coupang_product_url: String,
    val is_rocket_delivery: Boolean
) {
    companion object {
        fun of(ingredient: Ingredient, size: Double): IngredientResponse {
            return IngredientResponse(
                ingredient_id = ingredient.id,
                ingredient_name = ingredient.name,
                ingredient_type = getIngredientType(ingredient.ingredientType),
                ingredient_size = size,
                ingredient_unit = ingredient.ingredientUnitEnum,
                coupang_product_image = ingredient.coupangProductImage,
                coupang_product_name = ingredient.coupangProductName,
                coupang_product_price = ingredient.coupangProductPrice,
                coupang_product_url = ingredient.coupangUrl,
                is_rocket_delivery = ingredient.isRocketDelivery
            )
        }

        fun of(ingredient: Ingredient): IngredientResponse {
            return IngredientResponse(
                ingredient_id = ingredient.id,
                ingredient_name = ingredient.name,
                ingredient_type = getIngredientType(ingredient.ingredientType),
                ingredient_size = null,
                ingredient_unit = ingredient.ingredientUnitEnum,
                coupang_product_image = ingredient.coupangProductImage,
                coupang_product_name = ingredient.coupangProductName,
                coupang_product_price = ingredient.coupangProductPrice,
                coupang_product_url = ingredient.coupangUrl,
                is_rocket_delivery = ingredient.isRocketDelivery
            )
        }
    }
}