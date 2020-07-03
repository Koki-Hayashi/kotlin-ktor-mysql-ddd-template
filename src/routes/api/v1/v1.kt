package com.templatePJ.routes.api.v1

import io.ktor.routing.Route
import io.ktor.routing.route
import com.templatePJ.routes.api.v1.items.itemRoute
import com.templatePJ.routes.api.v1.users.userRoute
import com.templatePJ.routes.fallback


fun Route.v1() {

    route("/v1") {
        itemRoute()
        userRoute()

        fallback()
    }
}