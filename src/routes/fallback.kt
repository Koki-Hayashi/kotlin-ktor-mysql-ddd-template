package com.templatePJ.routes

import io.ktor.application.call
import io.ktor.http.HttpStatusCode
import io.ktor.request.uri
import io.ktor.response.respond
import io.ktor.routing.Route
import io.ktor.routing.route

fun Route.fallback() {
    route("/*") {
        handle {
            call.respond(
                HttpStatusCode.BadRequest,
                "There is no endpoint for ${call.request.uri}"
            )
        }
    }
}