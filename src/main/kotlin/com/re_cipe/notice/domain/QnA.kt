package com.re_cipe.notice.domain

import com.re_cipe.global.entity.BaseEntity
import javax.persistence.*

@Entity
class QnA constructor(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "qna_id")
    val id: Long = 0L,

    val question: String,

    @Lob
    val answer: String,

) : BaseEntity() {
}