package com.re_cipe.auth.oauth.util

import io.jsonwebtoken.Claims
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
class AppleClaimsValidator(
    @Value("\${spring.OAuth2.apple.iss}")
    private val iss: String,
    @Value("\${spring.OAuth2.apple.client-id}")
    private val clientId: String,
    @Value("\${spring.OAuth2.apple.nonce}")
    nonce: String
) {
    private val nonce: String = EncryptUtils.encrypt(nonce)

    fun isValid(claims: Claims): Boolean {
        return claims.issuer.contains(iss) &&
                claims.audience == clientId &&
                claims.get(NONCE_KEY, String::class.java) == nonce
    }

    companion object {
        private const val NONCE_KEY = "nonce"
    }
}
