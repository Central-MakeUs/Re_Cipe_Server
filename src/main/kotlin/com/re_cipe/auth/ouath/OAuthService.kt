package com.re_cipe.auth.ouath

interface OAuthService {
    fun requestToken(authCode: String): String
    fun getUserEmail(token: String): String
    fun getUserInfo(token: String): UserInfo
}