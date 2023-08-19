package com.re_cipe.search.domain

import com.re_cipe.global.entity.BaseEntity
import javax.persistence.*

@Entity
class Keyword(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "keyword_id")
    val id: Long = 0L,

    @Column(unique = true)
    val word: String,
    var count: Int = 1
) : BaseEntity() {

}