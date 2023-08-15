package com.re_cipe.recipe.domain.repository

import com.re_cipe.member.domain.Member
import com.re_cipe.recipe.domain.SavedShortFormRecipe
import org.springframework.data.jpa.repository.JpaRepository

interface SavedShortFormRepositoryCustom {
    fun unsaveShortFormRecipe(member: Member, shortFormRecipeId: Long): Boolean
}
