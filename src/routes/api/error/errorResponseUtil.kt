package com.templatePJ.routes.api.error

import com.templatePJ.routes.error.ErrorDto
import com.templatePJ.routes.error.ErrorMessage
import io.ktor.application.ApplicationCall
import io.ktor.http.HttpStatusCode
import io.ktor.response.respond

suspend fun errorResponseIfNotEmpty(errorMessages: List<ErrorMessage>, call: ApplicationCall) {
    if (errorMessages.isNotEmpty()) {
        call.respond(
            HttpStatusCode.BadRequest,
            ErrorDto(errorMessages.joinToString(separator = ", "))
        )
    }
}