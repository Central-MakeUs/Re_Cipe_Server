package com.re_cipe.auth.ui

import com.re_cipe.auth.service.AuthService
import com.re_cipe.auth.ui.dto.*
import com.re_cipe.global.annotation.CurrentMember
import com.re_cipe.global.response.ApiResponse
import com.re_cipe.jwt.util.JwtTokens
import com.re_cipe.member.domain.Member
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Api(value = "사용자 인증 관련 엔드포인트", tags = ["oauth api"])
@RestController
@RequestMapping("/api/v1/auth")
class AuthController(
    private val authService: AuthService
) {
    @ApiOperation(value = "구글 로그인", notes = "구글 로그인을 진행합니다.")
    @PostMapping("/google/signin")
    fun googleSignIn(
        @ApiParam(value = "구글 Access Token", required = true)
        @RequestHeader(value = "auth-token") code: String
    ): ApiResponse<GoogleSignInResponse> {
        return ApiResponse.success(authService.googleSignIn(code))
    }

    @ApiOperation(value = "구글 회원가입", notes = "구글 회원가입을 진행합니다.")
    @PostMapping("/google/signup")
    fun googleSignup(
        @ApiParam(value = "구글 Access Token", required = true)
        @RequestHeader(value = "auth-token") token: String,
        @RequestBody googleSignUpRequest: GoogleSignUpRequest
    ): ApiResponse<JwtTokens> {
        return ApiResponse.success(authService.googleSignup(token, googleSignUpRequest))
    }

    @ApiOperation(value = "애플 로그인", notes = "애플 로그인을 진행합니다.")
    @PostMapping("/apple/signin")
    fun appleSignIn(
        @ApiParam(value = " 토큰", required = true)
        @RequestBody appleSignInRequest: AppleSignInRequest
    ): ApiResponse<AppleSignInResponse> {
        return ApiResponse.success(authService.appleSignIn(appleSignInRequest))
    }

    @ApiOperation(value = "애플 회원가입", notes = "애플 회원가입을 진행합니다.")
    @PostMapping("/apple/signup")
    fun appleSignUp(
        @ApiParam(value = "애플 identify token", required = true)
        @RequestBody appleSignUpRequest: AppleSignUpRequest
    ): ApiResponse<JwtTokens> {
        return ApiResponse.success(authService.appleSignup(appleSignUpRequest))
    }

    @ApiOperation(value = "JWT 발급 새로 고침", notes = "refreshToken을 사용하여 JWT를 새로고침 합니다.")
    @PostMapping("/refresh")
    fun refresh(@RequestHeader(value = "Refresh-Token") refreshToken: String): ApiResponse<JwtTokens> {
        return ApiResponse.success(authService.refresh(refreshToken))
    }

    @ApiOperation(value = "로그아웃 진행", notes = "로그아웃을 진행합니다.")
    @PostMapping("/logout")
    fun logout(
        @ApiParam(value = "Access Token", required = true)
        @RequestHeader(value = "Authorization") accessToken: String,
        @ApiParam(value = "Refresh Token", required = true)
        @RequestHeader(value = "Refresh-Token") refreshToken: String
    ): ApiResponse<Nothing> {
        authService.logout(accessToken, refreshToken)
        return ApiResponse.success()
    }

    @ApiOperation(value = "로그인한 사용자 탈퇴하기", notes = "현재 로그인한 사용자를 탈퇴합니다.")
    @PostMapping("/withdrawal")
    fun withdraw(@CurrentMember member: Member): ApiResponse<Nothing> {
        authService.withdraw(member.id)
        return ApiResponse.success()
    }
}