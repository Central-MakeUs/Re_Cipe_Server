package com.re_cipe.ingredient.domain

import javax.persistence.*

@Entity
class Ingredient constructor(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ingredient_id")
    val id: Long = 0L,

    val name: String,

    val ingredientType: IngredientType,

    val ingredientUnit: Unit
) {
}