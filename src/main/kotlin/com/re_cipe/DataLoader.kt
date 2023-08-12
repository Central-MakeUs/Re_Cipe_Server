package com.re_cipe

import com.re_cipe.ingredient.domain.Ingredient
import com.re_cipe.ingredient.domain.IngredientType
import com.re_cipe.ingredient.domain.UnitEnum
import com.re_cipe.ingredient.domain.repository.IngredientRepository
import lombok.RequiredArgsConstructor
import lombok.extern.slf4j.Slf4j
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Transactional
@Component
@RequiredArgsConstructor
@Slf4j
class DataLoader(
    val ingredientRepository: IngredientRepository
) : ApplicationRunner {
    val dataLoaderOn: Boolean = true
    override fun run(args: ApplicationArguments) {
        if (dataLoaderOn)
            runDataLoader()
    }

    @Throws(InterruptedException::class)
    private fun runDataLoader() {
        val ingredient1 = Ingredient(
            name = "토마토",
            ingredientType = IngredientType.VEGETABLE,
            ingredientUnitEnum = UnitEnum.PIECE
        )
        ingredientRepository.save(ingredient1)
        val ingredient2 = Ingredient(
            name = "바나나",
            ingredientType = IngredientType.FRUIT,
            ingredientUnitEnum = UnitEnum.PIECE
        )
        ingredientRepository.save(ingredient2)
    }
}