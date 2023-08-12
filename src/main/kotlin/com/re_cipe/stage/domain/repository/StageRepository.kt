package com.re_cipe.stage.domain.repository

import com.re_cipe.stage.domain.RecipeStage
import org.springframework.data.jpa.repository.JpaRepository

interface StageRepository : JpaRepository<RecipeStage, Long>, StageRepositoryCustom {
}