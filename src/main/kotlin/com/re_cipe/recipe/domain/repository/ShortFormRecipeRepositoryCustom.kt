package com.re_cipe.recipe.domain.repository

import com.re_cipe.recipe.domain.Recipe
import com.re_cipe.recipe.domain.ShortFormRecipe
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Slice

interface ShortFormRecipeRepositoryCustom {
    fun findShortFormRecipes(pageable: Pageable): Slice<ShortFormRecipe>
    fun deleteShortFormRecipe(shortFormId: Long): Boolean
    fun searchRecipeByKeyword(keyword: String, pageable: Pageable): Slice<ShortFormRecipe>
}