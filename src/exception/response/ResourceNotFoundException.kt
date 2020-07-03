package com.templatePJ.exception.response

import io.ktor.http.HttpStatusCode

class ResourceNotFoundException(message: String) :
    TemaplteProjectResponseException(
        HttpStatusCode.NotFound,
        message
    )
