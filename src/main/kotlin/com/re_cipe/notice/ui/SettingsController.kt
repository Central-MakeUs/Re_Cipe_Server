package com.re_cipe.notice.ui

import com.re_cipe.global.response.ApiResponse
import com.re_cipe.notice.ui.dto.NoticeRequest
import com.re_cipe.notice.service.NoticeService
import com.re_cipe.notice.ui.dto.QnARequest
import com.re_cipe.notice.ui.dto.QnAResponse
import com.re_cipe.notice.ui.dto.NoticeResponse
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import org.springframework.web.bind.annotation.*

@RequestMapping("/api/v1/etc")
@RestController
@Api(tags = ["etc api"], description = "Re:cipe API")
class SettingsController(val noticeService: NoticeService,
    ) {
    @ApiOperation(value = "공지사항을 조회한다.")
    @SecurityRequirement(name = "Authorization")
    @GetMapping("/notice")
    fun getNoticeLists(
    ): ApiResponse<List<NoticeResponse>> {
        return ApiResponse.success(noticeService.findAllNotice())
    }

    @ApiOperation(value = "공지사항을 생성한다.")
    @SecurityRequirement(name = "Authorization")
    @PostMapping("/notice")
    fun createNotice(
        @RequestBody noticeRequest: NoticeRequest
    ): ApiResponse<Long> {
        return ApiResponse.success(noticeService.createNotice(noticeRequest))
    }

    @ApiOperation(value = "공지사항을 삭제한다.")
    @SecurityRequirement(name = "Authorization")
    @DeleteMapping("/notice/{notice-id}")
    fun deleteNotice(
        @PathVariable("notice-id") noticeId: Long
    ): ApiResponse<Boolean> {
        return ApiResponse.success(noticeService.deleteNotice(noticeId))
    }

    @ApiOperation("value = QnA를 조회한다.")
    @SecurityRequirement(name = "Authorization")
    @GetMapping("/qna")
    fun getQnALists(
    ): ApiResponse<List<QnAResponse>> {
        return ApiResponse.success(noticeService.findAllQnA())
    }

    @ApiOperation(value = "QnA을 생성한다.")
    @SecurityRequirement(name = "Authorization")
    @PostMapping("/qna")
    fun createQnA(
        @RequestBody qnARequest: QnARequest
    ): ApiResponse<Long> {
        return ApiResponse.success(noticeService.createQnA(qnARequest))
    }

    @ApiOperation(value = "QnA을 삭제한다.")
    @SecurityRequirement(name = "Authorization")
    @DeleteMapping("/qna/{qna-id}")
    fun deleteQnA(
        @PathVariable("qna-id") qnaId: Long
    ): ApiResponse<Boolean> {
        return ApiResponse.success(noticeService.deleteQnA(qnaId))
    }
}