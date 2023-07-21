package com.re_cipe.auth.oauth

import com.re_cipe.auth.oauth.util.ApplePublicKeys
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping

@FeignClient(url = "https://appleid.apple.com/auth", name = "apple-public-key-client")
interface AppleClient {
    @GetMapping(value = ["/keys"], consumes = ["application/json"])
    fun getApplePublicKeys(): ApplePublicKeys
}