package com.re_cipe.ingredient.domain

import javax.persistence.*

@Entity
class Ingredient constructor(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ingredient_id")
    val id: Long = 0L,

    val name: String,

    @Enumerated(EnumType.STRING)
    val ingredientType: IngredientType,

    @Enumerated(EnumType.STRING)
    val ingredientUnitEnum: UnitEnum,

    @Lob
    val coupangUrl: String,

    @Lob
    val coupangProductImage: String,

    val coupangProductName: String,

    val coupangProductPrice: Int,
    val isRocketDelivery: Boolean
) {
}