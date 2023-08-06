package com.re_cipe.ingredient.domain

import com.re_cipe.recipe.domain.Recipe
import javax.persistence.*

@Entity
class RecipeIngredients constructor(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "recipe_ingredient_id")
    val id: Long = 0L,


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recipe_id", foreignKey = ForeignKey(ConstraintMode.NO_CONSTRAINT))
    val recipe: Recipe,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ingredient_id", foreignKey = ForeignKey(ConstraintMode.NO_CONSTRAINT))
    val ingredient: Ingredient,
) {
}