package com.re_cipe.ingredient.domain.repository

import com.re_cipe.ingredient.domain.Ingredient
import com.re_cipe.ingredient.domain.RecipeIngredients
import org.springframework.data.jpa.repository.JpaRepository

interface IngredientRepository: JpaRepository<Ingredient, Long>, IngredientRepositoryCustom {
}