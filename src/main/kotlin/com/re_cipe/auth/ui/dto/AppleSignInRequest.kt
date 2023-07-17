package com.re_cipe.auth.ui.dto

import com.re_cipe.jwt.util.JwtTokens
import com.fasterxml.jackson.annotation.JsonProperty
import javax.validation.constraints.NotBlank

data class AppleSignInRequest(
    @NotBlank
    val idToken: String,

    val email: String
)