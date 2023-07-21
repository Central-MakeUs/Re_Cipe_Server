package com.re_cipe.auth.oauth

import com.re_cipe.auth.oauth.util.ApplePublicKey
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class AppleClientTest {

    @Autowired
    private lateinit var appleClient: AppleClient

    @Test
    @DisplayName("apple 서버와 통신하여 Apple public keys 응답을 받는다")
    fun getPublicKeys() {
        val applePublicKeys = appleClient.getApplePublicKeys()
        val keys = applePublicKeys.keys

        val isRequestedKeysNonNull = keys.all(this::isAllNotNull)
        assertThat(isRequestedKeysNonNull).isTrue()
    }

    private fun isAllNotNull(applePublicKey: ApplePublicKey): Boolean {
        return applePublicKey.kty != null && applePublicKey.kid != null &&
                applePublicKey.use != null && applePublicKey.alg != null &&
                applePublicKey.n != null && applePublicKey.e != null
    }
}
