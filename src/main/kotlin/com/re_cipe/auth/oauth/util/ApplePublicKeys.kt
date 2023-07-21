package com.re_cipe.auth.oauth.util

import java.lang.IllegalArgumentException

data class ApplePublicKeys (
    val keys: List<ApplePublicKey>
) {
    fun getMatchesKey(alg: String, kid: String): ApplePublicKey {
        return this.keys
            .firstOrNull { k -> k.alg == alg && k.kid == kid }
            ?: throw IllegalArgumentException("Apple JWT 값의 alg, kid 정보가 올바르지 않습니다.")
    }
}
