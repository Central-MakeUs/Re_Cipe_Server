package com.re_cipe.member.ui.dto

import com.re_cipe.member.domain.Member
import com.re_cipe.member.domain.Provider

data class MemberResponse(
    val memberId: Long,
    val email: String,
    val nickname: String,
    val provider: Provider
) {
    companion object {
        fun of(member: Member): MemberResponse {
            return MemberResponse(
                memberId = member.id,
                email = member.email,
                nickname = member.nickname,
                provider = member.provider
            )
        }
    }
}