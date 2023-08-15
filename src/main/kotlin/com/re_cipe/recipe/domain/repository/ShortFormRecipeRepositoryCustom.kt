package com.re_cipe.recipe.domain.repository

import com.re_cipe.recipe.domain.ShortFormRecipe
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Slice

interface ShortFormRecipeRepositoryCustom {
    fun findShortFormRecipes(pageable: Pageable): Slice<ShortFormRecipe>
}