package com.re_cipe.member.domain.repository

import com.querydsl.jpa.impl.JPAQueryFactory
import com.re_cipe.global.entity.EntityStatus
import com.re_cipe.member.domain.Member
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

    override fun setNickname(memberId: Long, nickname: String) {
        queryFactory.update(member)
            .set(member.nickname, nickname)
            .where(member.id.eq(memberId))
            .execute()
    }

    override fun existsByEmail(email: String): Boolean {
        val content = queryFactory.selectFrom(member)
            .where(member.email.eq(email))
            .fetch()
        if(content.isEmpty()){
            return false
        }
        return true
    }

    override fun findByEmail(email: String): Member? {
        return queryFactory.selectFrom(member)
            .where(member.email.eq(email).and(member.isDeleted.isFalse).and(member.status.eq(EntityStatus.ACTIVE)))
            .fetchOne()
    }
}