package com.re_cipe.exception

class BusinessException(
    val errorCode: ErrorCode
) : RuntimeException()