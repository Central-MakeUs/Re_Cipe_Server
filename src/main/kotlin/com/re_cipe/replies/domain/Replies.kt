package com.re_cipe.replies.domain

import com.re_cipe.comments.domain.Comments
import com.re_cipe.global.entity.BaseEntity
import com.re_cipe.member.domain.Member
import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction
import javax.persistence.*

@Entity
class Replies constructor(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "replies_id")
    val id: Long = 0L,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comments_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    val comment: Comments,

    val isDeleted: Boolean = false
) : BaseEntity() {

}