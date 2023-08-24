package com.re_cipe.recipe.ui.dto

data class ShortFormRecipeCreateRequest(
    val shortform_name: String,
    val description: String,
    val video_url: String,
    val video_time: String,
    val ingredients_ids: MutableList<Long>
)