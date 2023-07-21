package com.re_cipe.auth.oauth

data class GoogleOauthToken(
    val access_token: String? = null,
    val expires_in: Int? = 0,
    val scope: String? = null,
    val token_type: String? = null,
    val id_token: String? = null,
)