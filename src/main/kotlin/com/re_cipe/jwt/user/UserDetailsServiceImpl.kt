package com.re_cipe.jwt.user

import com.re_cipe.exception.BusinessException
import com.re_cipe.exception.ErrorCode
import com.re_cipe.member.domain.repository.MemberRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class UserDetailsServiceImpl(
    private val memberRepository: MemberRepository
) : UserDetailsService {
    override fun loadUserByUsername(useremail: String): UserDetails {
        return UserDetailsImpl(memberRepository.findByEmail(useremail)
            ?: throw BusinessException(ErrorCode.MEMBER_NOT_FOUND)
        )
    }
}