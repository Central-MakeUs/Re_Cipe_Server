package com.re_cipe.search.domain

import com.re_cipe.search.domain.Keyword
import org.springframework.data.jpa.repository.JpaRepository

interface KeywordRepository : JpaRepository<Keyword, Long> {
    fun findAllByWord(word: String): List<Keyword>
}