package com.re_cipe.ingredient.domain

import com.re_cipe.recipe.domain.ShortFormRecipe
import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction
import javax.persistence.*

@Entity
class ShortFormIngredients(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "shortform_ingredient_id")
    val id: Long = 0L,


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shortform_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    val shortFormRecipe: ShortFormRecipe,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ingredient_id")
    val ingredient: Ingredient
)