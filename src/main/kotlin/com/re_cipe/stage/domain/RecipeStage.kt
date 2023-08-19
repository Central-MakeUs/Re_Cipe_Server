package com.re_cipe.stage.domain

import com.re_cipe.recipe.domain.Recipe
import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction
import javax.persistence.*

@Entity
class RecipeStage constructor(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "stage_id")
    val id: Long = 0L,

    @Lob
    val image_url: String? = null,

    @Lob
    val stage_description: String,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recipe_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    val recipe: Recipe,
) {
}