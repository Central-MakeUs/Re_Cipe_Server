package com.re_cipe.auth.service


import com.re_cipe.auth.oauth.AppleOAuthUserProvider
import com.re_cipe.auth.oauth.GoogleOauthService
import com.re_cipe.auth.ui.dto.*
import com.re_cipe.exception.BusinessException
import com.re_cipe.exception.ErrorCode
import com.re_cipe.global.redis.RedisService
import com.re_cipe.jwt.service.JwtService
import com.re_cipe.jwt.util.JwtTokens
import com.re_cipe.member.domain.Member
import com.re_cipe.member.domain.Provider
import com.re_cipe.member.domain.repository.MemberRepository
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional


@Service
@Transactional
class AuthService(
    private val memberRepository: MemberRepository,
    private val googleOauthService: GoogleOauthService,
    private val appleOAuthUserProvider: AppleOAuthUserProvider,
    private val jwtService: JwtService,
    private val redisService: RedisService
) {

    @Value("\${jwt.refresh-token-expiry}")
    private val refreshTokenExpiry: Long = 0

    @Value("\${jwt.access-token-expiry}")
    private val accessTokenExpiry: Long = 0

    @Transactional
    fun googleSignIn(token: String): GoogleSignInResponse {
        val email = googleOauthService.getUserEmail(token)

        val isExist = memberRepository.existsByEmail(email)
        if (!isExist) {
            return GoogleSignInResponse(false, JwtTokens(accessToken = token))
        }

        val tokens = jwtService.issue(email)
        storeRefresh(tokens, email)
        return GoogleSignInResponse(true, tokens)
    }

    @Transactional
    fun googleSignup(token: String, googleSignUpRequest: GoogleSignUpRequest): JwtTokens {
        val userInfo = googleOauthService.getUserInfo(token)
        val isExist = memberRepository.existsByEmail(userInfo.email)
        if (isExist) {
            return jwtService.issue(userInfo.email)
        }
        join(email = userInfo.email, googleSignUpRequest = googleSignUpRequest)

        val tokens = jwtService.issue(userInfo.email)
        storeRefresh(tokens, userInfo.email)
        return tokens
    }

    @Transactional
    fun appleSignIn(appleSignInRequest: AppleSignInRequest): AppleSignInResponse {
        val applePlatformMember = appleOAuthUserProvider.getApplePlatformMember(appleSignInRequest.idToken)
        val isExist = memberRepository.existsByEmail(applePlatformMember.email)
        if (!isExist) {
            return AppleSignInResponse(false, JwtTokens(accessToken = appleSignInRequest.idToken))
        }

        val tokens = jwtService.issue(applePlatformMember.email)
        storeRefresh(tokens, applePlatformMember.email)
        return AppleSignInResponse(true, tokens);
    }

    @Transactional
    fun appleSignup(appleSignUpRequest: AppleSignUpRequest): JwtTokens {
        val applePlatformMember = appleOAuthUserProvider.getApplePlatformMember(appleSignUpRequest.idToken)
        val isExist = memberRepository.existsByEmail(applePlatformMember.email)
        if (isExist) {
            return jwtService.issue(applePlatformMember.email)
        }
        join(appleSignUpRequest, applePlatformMember.email)

        val tokens = jwtService.issue(applePlatformMember.email)
        storeRefresh(tokens, applePlatformMember.email)
        return tokens;
    }

    @Transactional
    fun refresh(refreshToken: String): JwtTokens {
        val email = redisService.getValue("$REFRESH_TOKEN_PREFIX:${refreshToken}") as String?
            ?: throw BusinessException(ErrorCode.EXPIRED_REFRESH_TOKEN)

        val member: Member = memberRepository.findByEmail(email)
            ?: throw BusinessException(ErrorCode.MEMBER_NOT_FOUND)

        val jwtToken = jwtService.refresh(refreshToken, email, member.id)
        storeRefresh(jwtToken, member.email)
        redisService.deleteValue("$REFRESH_TOKEN_PREFIX:${refreshToken}")
        return jwtToken
    }

    @Transactional
    fun logout(accessToken: String, refreshToken: String) {
        redisService.getValue("$REFRESH_TOKEN_PREFIX:$refreshToken")
            ?: throw BusinessException(ErrorCode.INVALID_REFRESH_TOKEN)
        redisService.deleteValue("$REFRESH_TOKEN_PREFIX:$refreshToken")

        storeLogoutAccessToken(accessToken)
    }

    @Transactional
    fun withdraw(memberId: Long): Boolean {
        val member =
            memberRepository.findById(memberId).orElseThrow { throw BusinessException(ErrorCode.MEMBER_NOT_FOUND) }
        member.softDelete()
        member.isDeleted = true
        return true
    }

    private fun join(email: String, googleSignUpRequest: GoogleSignUpRequest) {
        memberRepository.save(
            Member(
                email = email,
                nickname = googleSignUpRequest.nickname,
                provider = Provider.GOOGLE
            )
        )
    }

    private fun join(appleSignUpRequest: AppleSignUpRequest, userEmail: String) {
        memberRepository.save(
            Member(
                email = userEmail,
                nickname = appleSignUpRequest.nickname,
                provider = Provider.APPLE
            )
        )
    }

    private fun storeRefresh(jwtToken: JwtTokens, email: String) {
        redisService.setValue(
            "$REFRESH_TOKEN_PREFIX:${jwtToken.refreshToken}",
            email,
            refreshTokenExpiry
        )
    }

    private fun storeLogoutAccessToken(accessToken: String) {
        redisService.setValue(
            "$LOGOUT_ACCESS_TOKEN_PREFIX:${accessToken}",
            "logout",
            accessTokenExpiry
        )
    }

    companion object {
        const val REFRESH_TOKEN_PREFIX = "refresh"
        const val LOGOUT_ACCESS_TOKEN_PREFIX = "logout"
    }
}