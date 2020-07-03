package com.templatePJ.exception.response

import io.ktor.http.HttpStatusCode
import com.templatePJ.exception.response.TemaplteProjectResponseException

class UnauthorizedException(message: String) :
    TemaplteProjectResponseException(
        HttpStatusCode.Unauthorized,
        message
    )
