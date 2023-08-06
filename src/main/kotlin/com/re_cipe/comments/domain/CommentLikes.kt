package com.re_cipe.comments.domain

import com.re_cipe.global.entity.BaseEntity
import com.re_cipe.member.domain.Member
import javax.persistence.*

@Entity
class CommentLikes constructor(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "likes_id")
    val id: Long = 0L,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    val likedBy: Member,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comments_id")
    val comments: Comments,
)