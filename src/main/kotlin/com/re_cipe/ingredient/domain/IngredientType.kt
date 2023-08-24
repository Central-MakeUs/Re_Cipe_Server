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

        fun stringToType(type: String): IngredientType {
            return when (type) {
                "VEGETABLE" -> VEGETABLE
                "FRUIT" -> FRUIT
                "MEAT" -> MEAT
                "SEAFOOD" -> SEAFOOD
                "DAIRY" -> DAIRY
                "SAUCE" -> SAUCE
                "OIL" -> OIL
                "POWDER" -> POWDER
                else -> throw IllegalArgumentException("Invalid ingredient type: $type")
            }
        }
    }
}
