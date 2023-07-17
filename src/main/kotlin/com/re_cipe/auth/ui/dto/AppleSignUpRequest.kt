package com.re_cipe.auth.ui.dto

import javax.validation.constraints.NotBlank

data class AppleSignUpRequest(
    @NotBlank
    val idToken: String,

    val email: String,
    val nickname: String
)