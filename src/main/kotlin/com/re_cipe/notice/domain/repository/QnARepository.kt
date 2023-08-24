package com.re_cipe.notice.domain.repository

import com.re_cipe.notice.domain.QnA
import org.springframework.data.jpa.repository.JpaRepository

interface QnARepository: JpaRepository<QnA, Long> {
}