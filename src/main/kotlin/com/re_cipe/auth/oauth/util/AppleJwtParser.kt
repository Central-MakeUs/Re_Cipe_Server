package com.re_cipe.auth.oauth.util

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import com.re_cipe.exception.BusinessException
import com.re_cipe.exception.ErrorCode
import io.jsonwebtoken.*
import org.springframework.stereotype.Component
import org.springframework.util.Base64Utils
import java.security.PublicKey

@Component
class AppleJwtParser {

    companion object {
        private const val IDENTITY_TOKEN_VALUE_DELIMITER = "."
        private const val HEADER_INDEX = 0
        private val OBJECT_MAPPER = ObjectMapper()
    }

    fun parseHeaders(identityToken: String): Map<String, String> {
        try {
            val encodedHeader = identityToken.split(IDENTITY_TOKEN_VALUE_DELIMITER)[HEADER_INDEX]
            val decodedHeader = String(Base64Utils.decodeFromUrlSafeString(encodedHeader))

            return OBJECT_MAPPER.readValue(decodedHeader, object : TypeReference<Map<String, String>>() {})
        } catch (e: Exception) {
            throw IllegalStateException("Apple OAuth 로그인 중 parseHeaders에 문제가 발생했습니다.")
        }
    }


    fun parsePublicKeyAndGetClaims(idToken: String, publicKey: PublicKey): Claims {
        try {
            return Jwts.parserBuilder()
                .setSigningKey(publicKey)
                .build()
                .parseClaimsJws(idToken)
                .body
        } catch (e: ExpiredJwtException) {
            throw BusinessException(ErrorCode.EXPIRED_JWT)
        } catch (e: Exception) {
            throw IllegalStateException("Apple OAuth 로그인 중 parsePublicKeyAndGetClaims 문제가 발생했습니다.")
        }
    }
}
