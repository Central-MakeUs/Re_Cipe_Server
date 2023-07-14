package com.re_cipe.auth.service


import com.re_cipe.auth.ouath.OAuthService
import com.re_cipe.auth.ui.dto.GoogleSignInResponse
import com.re_cipe.auth.ui.dto.GoogleSignUpRequest
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
@Transactional(readOnly = true)
class AuthService(
    private val memberRepository: MemberRepository,
    private val oAuthService: OAuthService,
    private val jwtService: JwtService,
    private val redisService: RedisService
) {
    @Value("\${jwt.refresh-token-expiry}")
    private val refreshTokenExpiry: Long = 0

    @Value("\${jwt.access-token-expiry}")
    private val accessTokenExpiry: Long = 0

    @Transactional
    fun signIn(code: String): GoogleSignInResponse {
        val token = oAuthService.requestToken(code)
        val email = oAuthService.getUserEmail(token)

        val isExist = memberRepository.existsByEmail(email)
        if (!isExist) {
            return GoogleSignInResponse(false, JwtTokens(accessToken = token))
        }

        val tokens = jwtService.issue(email)
        storeRefresh(tokens, email)
        return GoogleSignInResponse(true, tokens)
    }

    @Transactional
    fun signup(token: String, googleSignUpRequest: GoogleSignUpRequest): JwtTokens {
        val userInfo = oAuthService.getUserInfo(token)
        join(email = userInfo.email, picture = userInfo.picture, googleSignUpRequest = googleSignUpRequest)

        val tokens = jwtService.issue(userInfo.email)
        storeRefresh(tokens, userInfo.email)
        return tokens
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
        return true
    }

    private fun join(email: String, picture: String, googleSignUpRequest: GoogleSignUpRequest) {
        memberRepository.save(
            Member(
                email = email,
                nickname = googleSignUpRequest.nickname,
                profileImage = picture,
                provider = Provider.GOOGLE
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