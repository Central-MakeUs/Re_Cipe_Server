package com.re_cipe.auth.ui.dto

data class ApplePublicKeyResponse(
    var keys: List<Key> = ArrayList()
) {
    data class Key(
        var kty: String,
        var kid: String,
        var use: String,
        var alg: String,
        var n: String,
        var e: String
    )
}