package com.re_cipe.security.config

import com.re_cipe.jwt.interceptor.JwtInterceptor
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.web.SecurityFilterChain
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
@EnableWebSecurity
class SecurityConfig(
    private val jwtInterceptor: JwtInterceptor
) : WebMvcConfigurer {
    @Bean
    fun filterChain(http: HttpSecurity?): SecurityFilterChain {
        http!!
            .httpBasic().disable()
            .csrf().disable()
            .cors().disable()
        return http.build()
    }

    override fun addInterceptors(registry: InterceptorRegistry) {
        registry.addInterceptor(jwtInterceptor)
            .addPathPatterns("/api/**")
            .excludePathPatterns(
                "/api/v1/auth/signin",
                "/api/v1/auth/signup",
                "/api/v1/auth/refresh",
                "/swagger-ui/index.html"
            )
    }
}