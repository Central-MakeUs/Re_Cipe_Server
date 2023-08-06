package com.re_cipe.recipe.ui.dto

import org.springframework.web.bind.annotation.RequestPart
import org.springframework.web.multipart.MultipartFile

class RecipeStageRequest(
    val stage_description: String,
    val image_url: String?
)