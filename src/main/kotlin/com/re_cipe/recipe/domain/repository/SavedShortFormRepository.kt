package com.re_cipe.recipe.domain.repository

import com.re_cipe.member.domain.Member
import com.re_cipe.recipe.domain.SavedShortFormRecipe
import org.springframework.data.jpa.repository.JpaRepository

interface SavedShortFormRepository : JpaRepository<SavedShortFormRecipe, Long>, SavedShortFormRepositoryCustom {
    fun existsBySavedByAndShortFormRecipe_Id(savedBy: Member, shortFormRecipeId: Long): Boolean
}
