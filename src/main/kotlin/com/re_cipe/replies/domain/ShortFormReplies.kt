package com.re_cipe.replies.domain

import com.re_cipe.comments.domain.Comments
import com.re_cipe.comments.domain.ShortFormComments
import com.re_cipe.global.entity.BaseEntity
import com.re_cipe.member.domain.Member
import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction
import javax.persistence.*
@Entity
class ShortFormReplies constructor(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "shortform_replies_id")
    val id: Long = 0L,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shortform_comments_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    val comment: ShortFormComments,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    val writtenBy: Member,

    val content: String,

    @OneToMany(mappedBy = "replies", cascade = [CascadeType.ALL])
    val likes: MutableList<ShortFormReplyLikes>  = mutableListOf(),

    val isDeleted: Boolean = false
) : BaseEntity() {

}