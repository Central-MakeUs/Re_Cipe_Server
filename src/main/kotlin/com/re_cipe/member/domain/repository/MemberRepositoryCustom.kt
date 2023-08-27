package com.re_cipe.member.domain.repository

interface MemberRepositoryCustom {
    fun setSystemNotification(memberId: Long, notification: Boolean)
    fun setMarketingNotification(memberId: Long, notification: Boolean)
    fun setNickname(memberId: Long, nickname: String)
}