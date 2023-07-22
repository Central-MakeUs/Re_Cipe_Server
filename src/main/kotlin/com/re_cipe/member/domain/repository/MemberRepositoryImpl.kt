package com.re_cipe.member.domain.repository

import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Repository
import javax.persistence.EntityManager

import com.re_cipe.member.domain.QMember.member

@Repository
class MemberRepositoryImpl(entityManager: EntityManager) : MemberRepositoryCustom {
    private val queryFactory: JPAQueryFactory = JPAQueryFactory(entityManager)

    override fun setSystemNotification(memberId: Long, notification: Boolean) {
        queryFactory.update(member)
            .set(member.system_notification, notification)
            .where(member.id.eq(memberId))
            .execute()
    }

    override fun setMarketingNotification(memberId: Long, notification: Boolean) {
        queryFactory.update(member)
            .set(member.marketing_notification, notification)
            .where(member.id.eq(memberId))
            .execute()
    }
}