package com.re_cipe

import lombok.RequiredArgsConstructor
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequiredArgsConstructor
class HomeController {
    @GetMapping("/")
    fun hello(): String {
        return "Hi kotlin"
    }
}