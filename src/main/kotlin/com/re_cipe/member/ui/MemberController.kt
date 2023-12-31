package com.re_cipe.member.ui

import com.re_cipe.global.annotation.CurrentMember
import com.re_cipe.global.response.ApiResponse
import com.re_cipe.member.domain.Member
import com.re_cipe.member.service.MemberService
import com.re_cipe.member.ui.dto.*
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import io.swagger.v3.oas.annotations.security.SecurityRequirement
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

    @ApiOperation(value = "나의 정보 조회", notes = "Access Token 필요")
    @SecurityRequirement(name = "Authorization")
    @GetMapping("/v1/users/me")
    fun getUserInformation(
        @CurrentMember member: Member
    ): ApiResponse<MemberResponse> {
        return ApiResponse.success(memberService.getOneUser(member))
    }

    @ApiOperation(value = "시스템 알림 설정", notes = "Access Token 필요")
    @SecurityRequirement(name = "Authorization")
    @PutMapping("/v1/users/system-notification")
    fun setSystemNotification(
        @CurrentMember member: Member,
        @RequestBody systemNotificationRequest: SystemNotificationRequest
    ): ApiResponse<Boolean> {
        return ApiResponse.success(memberService.setSystemNotification(member, systemNotificationRequest))
    }

    @ApiOperation(value = "마케팅 알림 설정", notes = "Access Token 필요")
    @SecurityRequirement(name = "Authorization")
    @PutMapping("/v1/users/marketing-notification")
    fun setMarketingNotification(
        @CurrentMember member: Member,
        @RequestBody marketingNotificationRequest: MarketingNotificationRequest
    ): ApiResponse<Boolean> {
        return ApiResponse.success(memberService.setMarketingNotification(member, marketingNotificationRequest))
    }

    @ApiOperation(value = "닉네임 변경", notes = "Access Token 필요")
    @SecurityRequirement(name = "Authorization")
    @PostMapping("/v1/users/change-nickname")
    fun changeNickname(
        @CurrentMember member: Member,
        @RequestBody nicknameChangeRequest: NicknameChangeRequest
    ): ApiResponse<Boolean> {
        return ApiResponse.success(memberService.changeNickname(nicknameChangeRequest, member))
    }
}