package com.templatePJ.routes.api

import io.ktor.routing.Route
import io.ktor.routing.route
import com.templatePJ.routes.api.v1.v1
import com.templatePJ.routes.fallback

fun Route.api() {

    route("/api") {
        v1()
        fallback()
    }
}