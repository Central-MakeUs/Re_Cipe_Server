package com.re_cipe.recipe.domain

import com.re_cipe.member.domain.Member
import javax.persistence.*

@Entity
class LikedShortFormRecipe(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "liked_shortformrecipe_id")
    val id: Long = 0L,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    val likedBy: Member,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shortform_id")
    val shortFormRecipe: ShortFormRecipe,

    val isDeleted: Boolean = false
)