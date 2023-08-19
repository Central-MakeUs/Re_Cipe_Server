package com.re_cipe.comments.domain

import com.re_cipe.global.entity.BaseEntity
import com.re_cipe.member.domain.Member
import com.re_cipe.recipe.domain.Recipe
import com.re_cipe.replies.domain.Replies
import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction
import javax.persistence.*

@Entity
class Comments constructor(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comments_id")
    val id: Long = 0L,

    val content: String,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    val writtenBy: Member,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recipe_id")
    val recipe: Recipe,

    @OneToMany(mappedBy = "comment", cascade = [CascadeType.ALL])
    val replyList: MutableList<Replies> = mutableListOf(),

    @OneToMany(mappedBy = "comments", cascade = [CascadeType.ALL])
    val likes: MutableList<CommentLikes> = mutableListOf(),

    val isDeleted: Boolean = false

) : BaseEntity() {
}