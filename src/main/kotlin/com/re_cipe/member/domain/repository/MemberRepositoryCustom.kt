package com.re_cipe.member.domain.repository

import com.re_cipe.member.domain.Member

interface MemberRepositoryCustom {
    fun setSystemNotification(memberId: Long, notification: Boolean)
    fun setMarketingNotification(memberId: Long, notification: Boolean)
    fun setNickname(memberId: Long, nickname: String)
    fun findByEmail(email: String): Member?
    fun existsByEmail(email: String): Boolean
}