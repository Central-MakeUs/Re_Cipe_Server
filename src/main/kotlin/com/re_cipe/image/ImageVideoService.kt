package com.re_cipe.image

import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.model.CannedAccessControlList
import com.amazonaws.services.s3.model.ObjectMetadata
import com.amazonaws.services.s3.model.PutObjectRequest
import com.re_cipe.exception.BusinessException
import com.re_cipe.exception.ErrorCode
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MaxUploadSizeExceededException
import org.springframework.web.multipart.MultipartFile
import org.springframework.web.server.ResponseStatusException
import java.io.IOException
import java.util.*

@Service
class ImageVideoService(
    private val amazonS3: AmazonS3
) {
    @Value("\${cloud.aws.s3.bucket}")
    private val bucket: String? = null

    @Value("\${bucket.domain}")
    private val bucketHost: String? = null

    fun uploadImages(multipartFile: MultipartFile): String {
        val fileName = multipartFile.originalFilename?.let { createFileName(it) }
        val objectMetadata = ObjectMetadata()
        objectMetadata.contentLength = multipartFile.size
        objectMetadata.contentType = multipartFile.contentType
        try {
            multipartFile.inputStream.use { inputStream ->
                amazonS3.putObject(
                    PutObjectRequest(bucket + "/images", fileName, inputStream, objectMetadata)
                        .withCannedAcl(CannedAccessControlList.PublicRead)
                )
            }
        } catch (e: IOException) {
            throw BusinessException(ErrorCode.UPLOAD_FILE_FAILURE)
        } catch (e: MaxUploadSizeExceededException) {
            throw BusinessException(ErrorCode.OVER_FILE_UPLOAD_LIMIT)
        }

        return bucketHost + "images/" + fileName!!
    }

    fun uploadVideos(multipartFile: MultipartFile): String {
        val fileName = multipartFile.originalFilename?.let { createFileName(it) }
        val objectMetadata = ObjectMetadata()
        objectMetadata.contentLength = multipartFile.size
        objectMetadata.contentType = multipartFile.contentType
        try {
            multipartFile.inputStream.use { inputStream ->
                amazonS3.putObject(
                    PutObjectRequest(bucket + "/videos", fileName, inputStream, objectMetadata)
                        .withCannedAcl(CannedAccessControlList.PublicRead)
                )
            }
        } catch (e: IOException) {
            throw BusinessException(ErrorCode.UPLOAD_FILE_FAILURE)
        } catch (e: MaxUploadSizeExceededException) {
            throw BusinessException(ErrorCode.OVER_FILE_UPLOAD_LIMIT)
        }

        return bucketHost + "videos/"+ fileName!!
    }

    private fun createFileName(fileName: String): String {
        return UUID.randomUUID().toString() + getFileExtension(fileName)
    }

    private fun getFileExtension(fileName: String): String? {
        return try {
            fileName.substring(fileName.lastIndexOf("."))
        } catch (e: StringIndexOutOfBoundsException) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, "잘못된 형식의 파일 ($fileName) 입니다.")
        }
    }
}