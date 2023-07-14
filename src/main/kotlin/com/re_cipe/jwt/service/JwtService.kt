package com.re_cipe.jwt.service

import com.re_cipe.exception.BusinessException
import com.re_cipe.exception.ErrorCode
import com.re_cipe.jwt.util.JwtProvider
import com.re_cipe.jwt.util.JwtTokens
import com.re_cipe.member.domain.Member
import com.re_cipe.member.domain.repository.MemberRepository
import com.re_cipe.member.service.MemberService
import org.springframework.stereotype.Service
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

    fun findAccessTokenMember(accessToken: String): Member {
        val claimsJws = jwtProvider.parseToken(accessToken)
        val memberId = claimsJws.body["id"].toString().toLong()

        return memberRepository.findById(memberId).orElseThrow { throw BusinessException(ErrorCode.MEMBER_NOT_FOUND) }
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