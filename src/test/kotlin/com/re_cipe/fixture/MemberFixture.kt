package com.re_cipe.fixture

import com.re_cipe.member.domain.Member
import com.re_cipe.member.domain.Provider

object MemberFixture {
    fun createMemberChoco(): Member {
        return Member(
            nickname = "chocochip",
            email = "dev.chocochip@gmail.com",
            provider = Provider.GOOGLE
        )
    }
}