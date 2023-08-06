package com.re_cipe.reviews.domain

import com.re_cipe.member.domain.Member
import javax.persistence.*

@Entity
class ReviewsLikes constructor(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_likes_id")
    val id: Long = 0L,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    val likedBy: Member,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reviews_id")
    val reviews: Reviews,
) {
}