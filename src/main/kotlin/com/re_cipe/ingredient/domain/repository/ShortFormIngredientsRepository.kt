package com.re_cipe.ingredient.domain.repository

import com.re_cipe.ingredient.domain.ShortFormIngredients
import com.re_cipe.recipe.domain.repository.ShortFormRecipeRepositoryCustom
import org.springframework.data.jpa.repository.JpaRepository

interface ShortFormIngredientsRepository : JpaRepository<ShortFormIngredients, Long>, ShortFormIngredientsRepositoryCustom {
}