package com.re_cipe.notice.ui.dto

import com.re_cipe.notice.domain.QnA

data class QnAResponse(
    val id: Long,
    val question: String,
    val answer: String
) {
    companion object {
        fun of(qnA: QnA): QnAResponse {
            return QnAResponse(id = qnA.id, question = qnA.question, answer = qnA.answer)
        }
    }
}