package com.re_cipe.jwt.service

import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.ObjectMapper
import com.re_cipe.exception.BusinessException
import com.re_cipe.exception.ErrorCode
import com.re_cipe.jwt.util.JwtProvider
import com.re_cipe.jwt.util.JwtTokens
import com.re_cipe.member.domain.repository.MemberRepository
import com.re_cipe.member.service.MemberService
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.Jwts
import org.springframework.stereotype.Service
import java.io.UnsupportedEncodingException
import java.math.BigInteger
import java.security.KeyFactory
import java.security.NoSuchAlgorithmException
import java.security.PublicKey
import java.security.spec.InvalidKeySpecException
import java.security.spec.RSAPublicKeySpec
import java.util.*

@Service
class JwtService(
    private val jwtProvider: JwtProvider,
    private val memberService: MemberService,
    private val memberRepository: MemberRepository,
) {
    fun issue(email: String): JwtTokens {
        val member = memberService.findByEmail(email)
            ?: throw BusinessException(ErrorCode.MEMBER_NOT_FOUND)

        val accessToken: String = jwtProvider.createAccessToken(member.id, email)
        val refreshToken: String = jwtProvider.createRefreshToken()

        return JwtTokens(
            accessToken = accessToken,
            refreshToken = refreshToken
        )
    }


    fun refresh(refreshToken: String, email: String, memberId: Long): JwtTokens {
        if (isValidate(refreshToken)) {
            throw BusinessException(ErrorCode.EXPIRED_REFRESH_TOKEN)
        }
        val accessToken = jwtProvider.createAccessToken(memberId, email)

        var localRefreshToken = refreshToken
        if (isRefreshable(refreshToken)) {
            localRefreshToken = jwtProvider.createRefreshToken()
        }
        return JwtTokens(accessToken, localRefreshToken)
    }

    private fun isValidate(refreshToken: String): Boolean {
        val now = Date()
        return !jwtProvider.isExpired(refreshToken, now)
    }

    private fun isRefreshable(refreshToken: String): Boolean {
        val calendar = Calendar.getInstance(TimeZone.getTimeZone("Asia/Seoul"), Locale.KOREA)
        calendar.time = Date()
        calendar.add(Calendar.DATE, 3)
        return !jwtProvider.isExpired(refreshToken, calendar.time)
    }
}