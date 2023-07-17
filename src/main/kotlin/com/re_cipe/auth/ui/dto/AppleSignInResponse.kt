package com.re_cipe.auth.ui.dto

import com.fasterxml.jackson.annotation.JsonProperty
import com.re_cipe.jwt.util.JwtTokens

data class AppleSignInResponse(
    @get:JsonProperty("isMember")
    @param:JsonProperty("isMember")
    val isMember: Boolean,
    val jwtTokens: JwtTokens
)