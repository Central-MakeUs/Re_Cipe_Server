package com.re_cipe.replies.domain.repository

import com.querydsl.jpa.impl.JPAQueryFactory
import com.re_cipe.replies.domain.QReplies.replies
import org.springframework.stereotype.Repository
import javax.persistence.EntityManager

@Repository
class RepliesRepositoryImpl(entityManager: EntityManager) : RepliesRepositoryCustom {
    private val queryFactory: JPAQueryFactory = JPAQueryFactory(entityManager)

    override fun deleteReply(replyId: Long): Boolean {
        queryFactory.update(replies)
            .where(replies.id.eq(replyId))
            .set(replies.isDeleted, true)
            .execute()
        return true
    }
}