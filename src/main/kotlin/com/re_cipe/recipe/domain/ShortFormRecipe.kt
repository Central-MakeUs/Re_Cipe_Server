package com.re_cipe.recipe.domain

import com.re_cipe.comments.domain.ShortFormComments
import com.re_cipe.global.entity.BaseEntity
import com.re_cipe.ingredient.domain.ShortFormIngredients
import com.re_cipe.member.domain.Member
import javax.persistence.*

@Entity
class ShortFormRecipe constructor(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "shortform_id")
    val id: Long = 0L,

    val name: String,

    @Lob
    val description: String,

    @Lob
    val video_url: String,

    val video_time: String,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    val writtenBy: Member,

    @OneToMany(mappedBy = "shortFormRecipe", cascade = [CascadeType.ALL])
    val commentList: MutableList<ShortFormComments> = mutableListOf(),

    @OneToMany(mappedBy = "shortFormRecipe", cascade = [CascadeType.ALL])
    val savedList: MutableList<SavedShortFormRecipe> = mutableListOf(),

    @OneToMany(mappedBy = "shortFormRecipe", cascade = [CascadeType.ALL])
    val likedList: MutableList<LikedShortFormRecipe> = mutableListOf(),

    @OneToMany(mappedBy = "shortFormRecipe", cascade = [CascadeType.ALL])
    val ingredients: MutableList<ShortFormIngredients> = mutableListOf(),

    val isDeleted: Boolean = false
) : BaseEntity() {
}