package com.re_cipe.replies.domain.repository

import com.querydsl.jpa.impl.JPAQueryFactory
import com.re_cipe.replies.domain.QShortFormReplies.shortFormReplies
import org.springframework.stereotype.Repository
import javax.persistence.EntityManager

@Repository
class ShortFormRepliesRepositoryImpl(entityManager: EntityManager) : ShortFormRepliesRepositoryCustom {
    private val queryFactory: JPAQueryFactory = JPAQueryFactory(entityManager)

    override fun deleteReply(replyId: Long): Boolean {
        queryFactory.update(shortFormReplies)
            .where(shortFormReplies.id.eq(replyId))
            .set(shortFormReplies.isDeleted, true)
            .execute()
        return true
    }
}