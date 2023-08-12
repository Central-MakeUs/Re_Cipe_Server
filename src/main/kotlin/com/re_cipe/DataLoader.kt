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
            ingredientUnitEnum = UnitEnum.PIECE,
            coupangUrl = "https://www.coupang.com/vp/products/6867638074?itemId=16419036181&vendorItemId=83609949981&sourceType=srp_product_ads&clickEventId=f3c5da1b-975d-437d-910b-39df110cab60&korePlacement=15&koreSubPlacement=1&q=%ED%86%A0%EB%A7%88%ED%86%A0&itemsCount=36&searchId=5088ef5000ba403092b25c6ed22898a3&rank=0&isAddedCart="
        )
        ingredientRepository.save(ingredient1)
        val ingredient2 = Ingredient(
            name = "바나나",
            ingredientType = IngredientType.FRUIT,
            ingredientUnitEnum = UnitEnum.PIECE,
            coupangUrl = "https://www.coupang.com/vp/products/218520566?itemId=676622340&vendorItemId=4743644012&pickType=COU_PICK&q=%EB%B0%94%EB%82%98%EB%82%98&itemsCount=36&searchId=280e7f324c084ffaa8bd7f461d66a41b&rank=1&isAddedCart="
        )
        ingredientRepository.save(ingredient2)
    }
}