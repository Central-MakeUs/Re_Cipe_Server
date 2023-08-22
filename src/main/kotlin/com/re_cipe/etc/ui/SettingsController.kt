package com.re_cipe.etc.ui

import io.swagger.annotations.Api
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/api/v1/etc")
@RestController
@Api(tags = ["etc api"], description = "Re:cipe API")
class SettingsController {
}