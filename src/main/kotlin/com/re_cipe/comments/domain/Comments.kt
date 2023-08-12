package com.re_cipe.comments.domain

import com.re_cipe.global.entity.BaseEntity
import com.re_cipe.member.domain.Member
import com.re_cipe.recipe.domain.Recipe
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
    @OnDelete(action = OnDeleteAction.CASCADE)
    val recipe: Recipe,

    val isDeleted: Boolean = false

) : BaseEntity() {
}