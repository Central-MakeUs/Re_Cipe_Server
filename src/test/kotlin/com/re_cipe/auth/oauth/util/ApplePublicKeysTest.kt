package com.re_cipe.auth.oauth.util

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

class ApplePublicKeysTest {

    @Test
    @DisplayName("alg, kid 값을 받아 해당 apple public key를 반환한다")
    fun getMatchesKey() {
        val expected = ApplePublicKey("kty", "kid", "use", "alg", "n", "e")

        val applePublicKeys = ApplePublicKeys(listOf(expected))

        assertThat(applePublicKeys.getMatchesKey("alg", "kid")).isEqualTo(expected)
    }

    @Test
    @DisplayName("alg, kid 값에 잘못된 값이 들어오면 예외를 반환한다")
    fun getMatchesInvalidKey() {
        val expected = ApplePublicKey("kty", "kid", "use", "alg", "n", "e")

        val applePublicKeys = ApplePublicKeys(listOf(expected))

        assertThatThrownBy { applePublicKeys.getMatchesKey("invalid", "invalid") }
            .isInstanceOf(IllegalArgumentException::class.java)
    }
}
