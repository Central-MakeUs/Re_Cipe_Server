package com.re_cipe.notice.domain

import com.re_cipe.global.entity.BaseEntity
import javax.persistence.*

@Entity
class Notice constructor(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notice_id")
    val id: Long = 0L,

    val title: String,

    @Lob
    val content: String,

) : BaseEntity() {
}