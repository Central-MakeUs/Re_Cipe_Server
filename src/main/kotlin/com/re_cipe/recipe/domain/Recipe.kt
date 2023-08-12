package com.re_cipe.recipe.domain

import com.re_cipe.comments.domain.Comments
import com.re_cipe.global.entity.BaseEntity
import com.re_cipe.ingredient.domain.Ingredient
import com.re_cipe.ingredient.domain.RecipeIngredients
import com.re_cipe.member.domain.Member
import com.re_cipe.reviews.domain.Reviews
import com.re_cipe.stage.domain.RecipeStage
import javax.persistence.*

@Entity
class Recipe constructor(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "recipe_id")
    val id: Long = 0L,

    val name: String,

    val description: String,

    val cook_time: Int,

    val serving_size: Int,

    val thumbnail_img: String,

    var rating: Double = 0.0,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    val writtenBy: Member,

    @OneToMany(mappedBy = "recipe", cascade = [CascadeType.ALL])
    val stages: MutableList<RecipeStage> = mutableListOf(),

    @OneToMany(mappedBy = "recipe", cascade = [CascadeType.ALL])
    val ingredientList: MutableList<RecipeIngredients> = mutableListOf(),

    @OneToMany(mappedBy = "recipe", cascade = [CascadeType.ALL])
    val comments: MutableList<Comments> = mutableListOf(),

    @OneToMany(mappedBy = "recipe", cascade = [CascadeType.ALL])
    val reviewsList: MutableList<Reviews> = mutableListOf()

) : BaseEntity() {
    fun addReview(reviews: Reviews) {
        val totalScore = rating * this.reviewsList.size
        this.reviewsList.add(reviews)
        this.rating = (totalScore + reviews.rating) / this.reviewsList.size
    }

}