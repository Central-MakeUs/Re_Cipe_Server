package com.re_cipe.member.domain.repository

interface MemberRepositoryCustom {
    fun setSystemNotification(memberId: Long, notification: Boolean)
}