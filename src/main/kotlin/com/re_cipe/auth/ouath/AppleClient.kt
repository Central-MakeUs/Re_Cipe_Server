package com.re_cipe.auth.ouath

import com.re_cipe.auth.ui.dto.ApplePublicKeyResponse
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping

@FeignClient(url = "https://appleid.apple.com", name = "AppleClient")
interface AppleClient {

    @GetMapping(value = ["/auth/keys"], consumes = ["application/json"])
    fun getApplePublicKey(): ApplePublicKeyResponse
}