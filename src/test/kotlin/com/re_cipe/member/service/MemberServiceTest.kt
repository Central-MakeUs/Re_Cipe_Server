package com.re_cipe.member.service

import com.re_cipe.member.domain.Member
import com.re_cipe.member.domain.Provider
import com.re_cipe.member.domain.repository.MemberRepository
import com.re_cipe.member.ui.dto.MarketingNotificationRequest
import com.re_cipe.member.ui.dto.SystemNotificationRequest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension

@ExtendWith(SpringExtension::class)
@SpringBootTest
class MemberServiceTest {
    @Autowired
    private lateinit var memberRepository: MemberRepository

    @Autowired
    private lateinit var memberService: MemberService

    @BeforeEach
    fun setUp() {
        memberRepository.deleteAll()
    }

    @Test
    @DisplayName("시스템 알림 설정을 검증한다.")
    fun setSystemNotificationTest() {
        //given
        val systemNotificationRequest = SystemNotificationRequest(false)
        val member = Member(email = "email@naver.com", nickname = "nick", provider = Provider.GOOGLE)
        memberRepository.save(member)

        //when
        val result = memberService.setSystemNotification(member, systemNotificationRequest)

        //then
        assertEquals(true, result)
        assertEquals(memberRepository.findByEmail("email@naver.com")!!.system_notification, false)
    }

    @Test
    @DisplayName("마케팅 알림 설정을 검증한다.")
    fun setMarketingNotificationTest() {
        //given
        val marketingNotificationRequest = MarketingNotificationRequest(false)
        val member = Member(email = "email@naver.com", nickname = "nick", provider = Provider.GOOGLE)
        memberRepository.save(member)

        //when
        val result = memberService.setMarketingNotification(member, marketingNotificationRequest)

        //then
        assertEquals(true, result)
        assertEquals(memberRepository.findByEmail("email@naver.com")!!.marketing_notification, false)
    }
}
