package com.templatePJ.routes

import io.ktor.application.call
import io.ktor.response.respondText
import io.ktor.routing.Route
import io.ktor.routing.route

fun Route.ping() {

    route("/ping") {
        handle {
            call.respondText { "I'm alive" }
        }
    }
}