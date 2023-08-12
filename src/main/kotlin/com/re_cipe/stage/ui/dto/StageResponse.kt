package com.re_cipe.stage.ui.dto

import com.re_cipe.stage.domain.RecipeStage

data class StageResponse(
    val stage_image_url: String? = null,
    val stage_description: String,
){
    companion object {
        fun of(stage: RecipeStage): StageResponse {
            return StageResponse(
                stage_image_url = stage.image_url,
                stage_description = stage.stage_description
            )
        }
    }
}