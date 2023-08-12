package com.re_cipe.replies.domain

import com.re_cipe.comments.domain.Comments
import com.re_cipe.member.domain.Member
import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction
import javax.persistence.*

@Entity
class ReplyLikes constructor(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reply_likes_id")
    val id: Long = 0L,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    val likedBy: Member,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "replies_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    val replies: Replies,
) {
}