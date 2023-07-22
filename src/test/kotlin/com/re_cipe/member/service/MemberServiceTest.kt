package com.re_cipe.member.service

import com.re_cipe.exception.BusinessException
import com.re_cipe.exception.ErrorCode
import com.re_cipe.member.domain.Member
import com.re_cipe.member.domain.repository.MemberRepository
import com.re_cipe.member.ui.dto.NicknameDuplicationRequest
import com.re_cipe.member.ui.dto.NicknameDuplicationResponse
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

class MemberServiceTest {
    @Mock
    private lateinit var memberRepository: MemberRepository

    @InjectMocks
    private lateinit var memberService: MemberService

    @BeforeEach
    fun setUp() {
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun `test existsByNickname when nickname is valid`() {
        val request = NicknameDuplicationRequest("valid_nickname")
        val expectedResponse = NicknameDuplicationResponse(false)
        `when`(memberRepository.existsByNickname(request.nickname)).thenReturn(false)

        val response = memberService.existsByNickname(request)

        assertEquals(expectedResponse, response)
    }

    @Test
    fun `test existsByNickname when nickname is invalid`() {
        val request = NicknameDuplicationRequest("too_long_nickname")
        val expectedErrorCode = ErrorCode.INVALID_NICKNAME
        val expectedException = BusinessException(expectedErrorCode)

        assertThrows(BusinessException::class.java) {
            memberService.existsByNickname(request)
        }
    }
}
