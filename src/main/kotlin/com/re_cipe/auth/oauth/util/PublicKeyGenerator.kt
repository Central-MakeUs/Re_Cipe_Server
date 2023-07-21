package com.re_cipe.auth.oauth.util

import org.springframework.stereotype.Component
import org.springframework.util.Base64Utils
import java.math.BigInteger
import java.security.KeyFactory
import java.security.PublicKey
import java.security.spec.RSAPublicKeySpec

@Component
class PublicKeyGenerator {

    companion object {
        private const val SIGN_ALGORITHM_HEADER_KEY = "alg"
        private const val KEY_ID_HEADER_KEY = "kid"
        private const val POSITIVE_SIGN_NUMBER = 1
    }

    fun generatePublicKey(headers: Map<String, String>, applePublicKeys: ApplePublicKeys): PublicKey {
        val applePublicKey = applePublicKeys.getMatchesKey(
            headers.getValue(SIGN_ALGORITHM_HEADER_KEY), headers.getValue(KEY_ID_HEADER_KEY)
        )
        return generatePublicKeyWithApplePublicKey(applePublicKey)
    }

    private fun generatePublicKeyWithApplePublicKey(publicKey: ApplePublicKey): PublicKey {
        val nBytes = Base64Utils.decodeFromUrlSafeString(publicKey.n)
        val eBytes = Base64Utils.decodeFromUrlSafeString(publicKey.e)

        val n = BigInteger(POSITIVE_SIGN_NUMBER, nBytes)
        val e = BigInteger(POSITIVE_SIGN_NUMBER, eBytes)

        val publicKeySpec = RSAPublicKeySpec(n, e)

        try {
            val keyFactory = KeyFactory.getInstance(publicKey.kty)
            return keyFactory.generatePublic(publicKeySpec)
        } catch (exception: Exception) {
            throw IllegalStateException("Apple OAuth 로그인 중 public key 생성에 문제가 발생했습니다.")
        }
    }
}
