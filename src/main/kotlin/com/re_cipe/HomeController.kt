package com.re_cipe

import lombok.RequiredArgsConstructor
import org.springframework.beans.factory.annotation.Value
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequiredArgsConstructor
class HomeController {
    @Value("\${spring.OAuth2.google.client_id}")
    private lateinit var application_name: String
    @GetMapping("/")
    fun hello(): String {
        return application_name
    }
}