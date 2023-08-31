package com.re_cipe.ingredient.service

import com.re_cipe.ingredient.domain.Ingredient
import com.re_cipe.ingredient.domain.IngredientType
import com.re_cipe.ingredient.domain.UnitEnum
import com.re_cipe.ingredient.domain.repository.IngredientRepository
import com.re_cipe.ingredient.ui.dto.IngredientRequest
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

    @Transactional
    fun createIngredient(ingredientRequest: IngredientRequest): Long {

        return ingredientRepository.save(
            Ingredient(
                name = ingredientRequest.name,
                ingredientType = IngredientType.stringToType(ingredientRequest.ingredientType),
                ingredientUnitEnum = UnitEnum.stringToEnum(ingredientRequest.ingredientUnitEnum),
                coupangUrl = ingredientRequest.coupangUrl,
                coupangProductName = ingredientRequest.coupangProductName,
                coupangProductImage = ingredientRequest.coupangProductImage,
                coupangProductPrice = ingredientRequest.coupangProductPrice,
                isRocketDelivery = ingredientRequest.isRocketDelivery
            )
        ).id
    }
}