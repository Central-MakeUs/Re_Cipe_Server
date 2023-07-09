package com.re_cipe

import lombok.RequiredArgsConstructor
import org.springframework.beans.factory.annotation.Value
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequiredArgsConstructor
class HomeController() {
    @GetMapping("/")
    fun hello(): String {
        return "recipe backend server"
    }
}