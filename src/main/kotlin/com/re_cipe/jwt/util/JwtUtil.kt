package com.re_cipe.jwt.util

import com.re_cipe.jwt.user.UserDetailsImpl
import com.re_cipe.jwt.user.UserDetailsServiceImpl
import io.jsonwebtoken.ExpiredJwtException
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import javax.servlet.http.HttpServletRequest

@Component
@Transactional(readOnly = true)
class JwtUtil(
    @Value("\${jwt.access-token-header}") private val ACCESS_TOKEN_HEADER: String,
    @Value("\${jwt.refresh-token-header}") private val REFRESH_TOKEN_HEADER: String,
    private val userDetailsService: UserDetailsServiceImpl,
    private val jwtProvider: JwtProvider
) {
    fun getAuthentication(token: String): Authentication {
        val userDetails = userDetailsService.loadUserByUsername(getUserPk(token))
        return UsernamePasswordAuthenticationToken(userDetails, "", userDetails.authorities)
    }

    fun getFromSecurityContextHolder(): UserDetailsImpl {
        return SecurityContextHolder.getContext().authentication.principal as UserDetailsImpl
    }

    fun resolveAccessToken(request: HttpServletRequest): String {
        return request.getHeader(ACCESS_TOKEN_HEADER).replace("Bearer", "").trim()
    }

    private fun getUserPk(token: String): String {
        return try {
            jwtProvider.parseToken(token).body["email"].toString()
        } catch (e: ExpiredJwtException) {
            e.claims.subject
        }
    }
}