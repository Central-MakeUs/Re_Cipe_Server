package com.re_cipe.auth.ui

import com.re_cipe.auth.service.AuthService
import com.re_cipe.auth.ui.dto.GoogleSignInResponse
import com.re_cipe.auth.ui.dto.GoogleSignUpRequest
import com.re_cipe.global.annotation.CurrentMember
import com.re_cipe.global.response.ApiResponse
import com.re_cipe.jwt.util.JwtTokens
import com.re_cipe.member.domain.Member
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Api(tags = ["oauth api"], description = "Re:cipe API")
@RestController
@RequestMapping("/api/v1/auth")
class AuthController(
    private val authService: AuthService
) {
    @ApiOperation(value = "구글 로그인")
    @PostMapping("/google/signin")
    fun signIn(@RequestHeader(value = "auth-code") code: String): ApiResponse<GoogleSignInResponse> {
        return ApiResponse.success(authService.signIn(code))
    }

    @ApiOperation(value = "구글 회원가입")
    @PostMapping("/google/signup")
    fun signup(
        @RequestHeader(value = "auth-token") token: String,
        @RequestBody googleSignUpRequest: GoogleSignUpRequest
    ): ApiResponse<JwtTokens> {
        return ApiResponse.success(authService.signup(token, googleSignUpRequest))
    }

    @ApiOperation(value = "Refresh 토큰 갱신")
    @PostMapping("/refresh")
    fun refresh(@RequestHeader(value = "Refresh-Token") refreshToken: String): ApiResponse<JwtTokens> {
        return ApiResponse.success(authService.refresh(refreshToken))
    }

    @ApiOperation(value = "로그아웃")
    @PostMapping("/logout")
    fun logout(
        @RequestHeader(value = "Authorization") accessToken: String,
        @RequestHeader(value = "Refresh-Token") refreshToken: String
    ): ApiResponse<Nothing> {
        authService.logout(accessToken, refreshToken)
        return ApiResponse.success()
    }

    @ApiOperation(value = "탈퇴")
    @PostMapping("/withdrawal")
    fun withdraw(@CurrentMember member: Member): ApiResponse<Nothing> {
        authService.withdraw(member.id)
        return ApiResponse.success()
    }
}