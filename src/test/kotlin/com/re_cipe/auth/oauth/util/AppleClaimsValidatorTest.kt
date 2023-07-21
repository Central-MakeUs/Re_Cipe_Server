package com.re_cipe.auth.oauth.util

import io.jsonwebtoken.Jwts
import java.util.HashMap
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

class AppleClaimsValidatorTest {

    private val ISS = "iss"
    private val CLIENT_ID = "aud"
    private val NONCE = "nonce"
    private val NONCE_KEY = "nonce"

    private val appleClaimsValidator = AppleClaimsValidator(ISS, CLIENT_ID, NONCE)

    @Test
    @DisplayName("올바른 Claims 이면 true 반환한다")
    fun isValid() {
        val claimsMap = HashMap<String, Any>()
        claimsMap[NONCE_KEY] = EncryptUtils.encrypt(NONCE)

        val claims = Jwts.claims(claimsMap)
            .setIssuer(ISS)
            .setAudience(CLIENT_ID)

        assertThat(appleClaimsValidator.isValid(claims)).isTrue
    }

    @ParameterizedTest
    @DisplayName("nonce, iss, aud(client_id) 중 올바르지 않은 값이 존재하면 false 반환한다")
    @CsvSource(
        "invalid, iss, aud",
        "nonce, invalid, aud",
        "nonce, iss, invalid"
    )
    fun isInvalid(nonce: String, iss: String, clientId: String) {
        val claimsMap = HashMap<String, Any>()
        claimsMap[NONCE_KEY] = EncryptUtils.encrypt(nonce)

        val claims = Jwts.claims(claimsMap)
            .setIssuer(iss)
            .setAudience(clientId)

        assertThat(appleClaimsValidator.isValid(claims)).isFalse
    }
}
