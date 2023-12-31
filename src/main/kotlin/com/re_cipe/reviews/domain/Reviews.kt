package com.re_cipe.reviews.domain

import com.re_cipe.global.entity.BaseEntity
import com.re_cipe.image.ReviewImages
import com.re_cipe.member.domain.Member
import com.re_cipe.recipe.domain.Recipe
import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction
import javax.persistence.*

@Entity
class Reviews constructor(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reviews_id")
    val id: Long = 0L,
    val rating: Int,
    @Lob
    val content: String,

    @OneToMany(mappedBy = "reviews", cascade = [CascadeType.ALL])
    val images: MutableList<ReviewImages> = mutableListOf(),

    val isDeleted: Boolean = false,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recipe_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    val recipe: Recipe,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    val writtenBy: Member,

    @OneToMany(mappedBy = "reviews", cascade = [CascadeType.ALL])
    val likes: MutableList<ReviewsLikes> = mutableListOf()
) : BaseEntity() {
}