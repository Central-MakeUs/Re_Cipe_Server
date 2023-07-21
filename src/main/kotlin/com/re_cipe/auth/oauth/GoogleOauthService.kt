package com.re_cipe.auth.oauth

interface GoogleOauthService {
    fun requestToken(authCode: String): String
    fun getUserEmail(token: String): String
    fun getUserInfo(token: String): UserInfo
}