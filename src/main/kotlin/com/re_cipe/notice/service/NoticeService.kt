package com.re_cipe.notice.service

import com.re_cipe.notice.domain.Notice
import com.re_cipe.notice.domain.QnA
import com.re_cipe.notice.domain.repository.NoticeRepository
import com.re_cipe.notice.domain.repository.QnARepository
import com.re_cipe.notice.ui.dto.NoticeRequest
import com.re_cipe.notice.ui.dto.NoticeResponse
import com.re_cipe.notice.ui.dto.QnARequest
import com.re_cipe.notice.ui.dto.QnAResponse
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class NoticeService(val noticeRepository: NoticeRepository, val qnARepository: QnARepository) {
    fun findAllNotice(): List<NoticeResponse> {
        return noticeRepository.findAll().map { n -> NoticeResponse.of(n) }
    }

    fun createNotice(noticeRequest: NoticeRequest): Long {
        return noticeRepository.save(Notice(title = noticeRequest.title, content = noticeRequest.content)).id
    }

    fun deleteNotice(id: Long): Boolean {
        noticeRepository.deleteById(id)
        return true
    }

    fun findAllQnA(): List<QnAResponse> {
        return qnARepository.findAll().map { n -> QnAResponse.of(n) }
    }

    fun createQnA(qnARequest: QnARequest): Long {
        return qnARepository.save(QnA(question = qnARequest.question, answer = qnARequest.answer)).id
    }

    fun deleteQnA(id: Long): Boolean {
        qnARepository.deleteById(id)
        return true
    }
}