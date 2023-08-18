package com.re_cipe.recipe.domain

import com.re_cipe.member.domain.Member
import javax.persistence.*

@Entity
class SavedRecipe constructor(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "saved_recipe_id")
    val id: Long = 0L,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    val savedBy: Member,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recipe_id")
    val recipe: Recipe,

    val isDeleted: Boolean = false
)