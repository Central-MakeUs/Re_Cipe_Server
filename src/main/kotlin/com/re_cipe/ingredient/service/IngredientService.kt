package com.re_cipe.ingredient.service

import com.re_cipe.ingredient.domain.Ingredient
import com.re_cipe.ingredient.domain.repository.IngredientRepository
import com.re_cipe.ingredient.ui.dto.IngredientResponse
import com.re_cipe.ingredient.ui.dto.IngredientSimpleResponse
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class IngredientService(
    val ingredientRepository: IngredientRepository
) {
    fun findAllIngredients(): List<IngredientSimpleResponse> {
        return ingredientRepository.findAll().map { ingredient: Ingredient -> IngredientSimpleResponse.of(ingredient) }
    }
}