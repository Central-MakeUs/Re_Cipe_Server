package com.re_cipe.image

import com.re_cipe.comments.domain.Comments
import com.re_cipe.reviews.domain.Reviews
import javax.persistence.*

@Entity
class ReviewImages constructor(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_images_id")
    val id: Long = 0L,

    @Lob
    val image_url: String?,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reviews_id")
    val reviews: Reviews,
)