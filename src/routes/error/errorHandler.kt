package com.templatePJ.routes.error

import io.ktor.application.call
import io.ktor.features.StatusPages
import io.ktor.http.HttpStatusCode
import io.ktor.response.respond
import com.templatePJ.exception.response.TemaplteProjectResponseException
import mu.KotlinLogging

private val logger = KotlinLogging.logger {}

fun StatusPages.Configuration.handle() {
    // fallback to grab exceptions thrown on purpose but no handled
    exception<TemaplteProjectResponseException> { e ->
        logger.error { e.printStackTrace() }

        call.respond(
            e.status,
            ErrorDto(e.message)
        )
    }

    // final fallback to handle all exceptions
    exception<Exception> { e ->
        logger.error { e.printStackTrace() }

        call.respond(
            HttpStatusCode.InternalServerError,
            ErrorDto(e.localizedMessage ?: "Something went wrong")
        )
    }
}

