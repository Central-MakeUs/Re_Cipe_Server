package com.re_cipe.global.config

import com.re_cipe.ReCipeApplication
import org.springframework.cloud.openfeign.EnableFeignClients
import org.springframework.context.annotation.Configuration

@Configuration
@EnableFeignClients(basePackageClasses = [ReCipeApplication::class])
class FeignClientConfig
