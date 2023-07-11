package com.re_cipe.member.ui

import com.re_cipe.global.response.ApiResponse
import com.re_cipe.member.service.MemberService
import com.re_cipe.member.ui.dto.NicknameDuplicationRequest
import com.re_cipe.member.ui.dto.NicknameDuplicationResponse
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import org.springframework.web.bind.annotation.*

@RestController
@Api(tags = ["user api"], description = "Re:cipe API")
@RequestMapping("/api")
class MemberController(
    private val memberService: MemberService
) {

    @ApiOperation(value = "유저 닉네임 검증")
    @PostMapping("/v1/users/verify-nickname")
    fun verifyNickname(@RequestBody nicknameDuplicationRequest: NicknameDuplicationRequest): ApiResponse<NicknameDuplicationResponse> {
        return ApiResponse.success(memberService.existsByNickname(nicknameDuplicationRequest))
    }
}