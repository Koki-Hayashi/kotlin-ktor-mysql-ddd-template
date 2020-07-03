package com.templatePJ.exception.response

import io.ktor.http.HttpStatusCode

class ValidationException(message: String) :
    TemaplteProjectResponseException(
        HttpStatusCode.BadRequest,
        message
    )
