package com.re_cipe.ingredient.domain

enum class IngredientType {
    VEGETABLE,
    FRUIT,
    MEAT,
    SEAFOOD,
    DAIRY,
    SAUCE,
    OIL,
    POWDER;

    companion object {
        fun getIngredientType(type: IngredientType): String {
            return if (type in listOf(VEGETABLE, FRUIT, MEAT, SEAFOOD, DAIRY)) {
                "INGREDIENTS"
            } else {
                "SAUCE"
            }
        }
    }
}