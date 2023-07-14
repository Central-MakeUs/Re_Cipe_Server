package com.re_cipe.auth.ui.dto

import com.re_cipe.jwt.util.JwtTokens
import com.fasterxml.jackson.annotation.JsonProperty

data class GoogleSignInResponse(
    @get:JsonProperty("isMember")
    @param:JsonProperty("isMember")
    val isMember: Boolean,
    val jwtTokens: JwtTokens
)