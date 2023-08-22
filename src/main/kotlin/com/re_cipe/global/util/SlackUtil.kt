package com.re_cipe.global.util

import com.re_cipe.member.domain.Member
import com.re_cipe.reviews.domain.Reviews
import lombok.extern.slf4j.Slf4j
import net.gpedro.integrations.slack.SlackApi
import net.gpedro.integrations.slack.SlackMessage
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

@Service
@Slf4j
class SlackUtil {
    @Value("\${logging.slack.report-uri}")
    private val slackUrl: String? = null
    fun sendReviewReport(member: Member, review: Reviews) {
        val api = SlackApi(slackUrl)
        api.call(
            SlackMessage(
                "신고자 id= " + member.id +
                        "\n 신고자 email= " + member.email +
                        "\n 리뷰 content= " + review.content +
                        "\n 리뷰 id= " + review.id
            )
        )
    }
}