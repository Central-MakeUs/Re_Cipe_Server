package com.re_cipe.recipe.domain.repository

import com.re_cipe.member.domain.Member
import com.re_cipe.recipe.domain.LikedShortFormRecipe
import com.re_cipe.recipe.domain.SavedShortFormRecipe
import org.springframework.data.jpa.repository.JpaRepository

interface LikedShortFormRepository : JpaRepository<LikedShortFormRecipe, Long>, LikedShortFormRepositoryCustom {
    fun existsByLikedByAndShortFormRecipe_Id(likedBy: Member, shortFormRecipeId: Long): Boolean
}
