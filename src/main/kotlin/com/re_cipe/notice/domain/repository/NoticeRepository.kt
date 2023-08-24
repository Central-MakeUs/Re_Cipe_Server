package com.re_cipe.notice.domain.repository

import com.re_cipe.notice.domain.Notice
import org.springframework.data.jpa.repository.JpaRepository

interface NoticeRepository : JpaRepository<Notice, Long> {
}