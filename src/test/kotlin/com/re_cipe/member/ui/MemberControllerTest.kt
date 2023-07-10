package com.re_cipe.member.ui

import com.fasterxml.jackson.databind.ObjectMapper
import com.ninjasquad.springmockk.MockkBean
import com.re_cipe.ApiControllerTest
import com.re_cipe.member.service.MemberService
import com.re_cipe.member.ui.dto.NicknameDuplicationRequest
import com.re_cipe.member.ui.dto.NicknameDuplicationResponse
import io.mockk.every
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.http.MediaType
import org.springframework.restdocs.headers.HeaderDocumentation
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders
import org.springframework.restdocs.operation.preprocess.Preprocessors
import org.springframework.restdocs.payload.FieldDescriptor
import org.springframework.restdocs.payload.JsonFieldType
import org.springframework.restdocs.payload.PayloadDocumentation
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

@TestInstance(TestInstance.Lifecycle.PER_CLASS)

class MemberControllerTest : ApiControllerTest(uri = "/api/v1/users") {

    @MockkBean
    lateinit var memberService: MemberService

    @Test
    @DisplayName("닉네임의 유효성을 검사합니다.")
    fun `닉네임 중복 확인 API`() {
        val nicknameDuplicationRequest = NicknameDuplicationRequest("leah")
        every { memberService.existsByNickname(any()) }.returns(NicknameDuplicationResponse(false))

        val uri = "$uri/verify-nickname"
        mockMvc.perform(
            RestDocumentationRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .content(ObjectMapper().writeValueAsString(nicknameDuplicationRequest))
        )
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.jsonPath("$.code").value("SUCCESS"))
            .andDo(MockMvcResultHandlers.print())
            .andDo(
                MockMvcRestDocumentation.document(
                    "post-nickname-duplication", // docs directory name
                    Preprocessors.preprocessRequest(Preprocessors.prettyPrint()),
                    Preprocessors.preprocessResponse(Preprocessors.prettyPrint()),
                    PayloadDocumentation.responseFields(
                        PayloadDocumentation.beneathPath("data").withSubsectionId("data"),
                        *nicknameDuplicationDataResponseFieldsSnippet(),
                    )
                )
            )
    }

    private fun nicknameDuplicationDataResponseFieldsSnippet(): Array<FieldDescriptor> {
        return arrayOf(
            PayloadDocumentation.fieldWithPath("isDuplicated").description("중복되었다면 true, 중복되지 않는다면 false")
        )
    }
}