package com.re_cipe.security.config

import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
@EnableWebSecurity
class WebConfig : WebMvcConfigurer {
    override fun addCorsMappings(registry: CorsRegistry) {
        registry.addMapping("/**")
            .allowedMethods(
                HttpMethod.GET.name,
                HttpMethod.PATCH.name,
                HttpMethod.POST.name,
                HttpMethod.DELETE.name,
                HttpMethod.PUT.name,
                HttpMethod.OPTIONS.name,
            )
    }


}