package com.re_cipe.member.domain.repository

import com.re_cipe.member.domain.Member
import org.springframework.data.jpa.repository.JpaRepository

interface MemberRepository : JpaRepository<Member, Long> {
    fun existsByNickname(nickname: String): Boolean
}