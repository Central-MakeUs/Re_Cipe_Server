package com.re_cipe.image

import com.re_cipe.global.annotation.CurrentMember
import com.re_cipe.global.annotation.NonMember
import com.re_cipe.global.response.ApiResponse
import com.re_cipe.member.domain.Member
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@Api(tags = ["image/video api"], description = "Re:cipe API")
@RequestMapping("/api/v1/file")
@RestController
class ImageVideoController(
    private val imageVideoService: ImageVideoService
) {

    @ApiOperation(value = "이미지 업로드 API", notes = "Access Token 필요")
    @SecurityRequirement(name = "Authorization")
    @PostMapping("/upload/image")
    fun uploadImage(
//        @CurrentMember member: Member,
        @RequestPart("multipartFile") file: MultipartFile
    ): ApiResponse<String> {
        val uploadFiles = imageVideoService.uploadImages(file)
        return ApiResponse.success(uploadFiles)
    }

    @ApiOperation(value = "동영상 업로드 API", notes = "Access Token 필요")
    @SecurityRequirement(name = "Authorization")
    @PostMapping("/upload/video")
    fun uploadVideo(
//        @CurrentMember member: Member,
        @RequestPart("multipartFile") file: MultipartFile
    ): ApiResponse<String> {
        val uploadFiles = imageVideoService.uploadVideos(file)
        return ApiResponse.success(uploadFiles)
    }
}