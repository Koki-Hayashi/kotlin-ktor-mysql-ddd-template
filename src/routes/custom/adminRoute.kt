package com.templatePJ.routes.custom

import io.ktor.application.ApplicationCallPipeline
import io.ktor.routing.Route
import io.ktor.routing.RouteSelector
import io.ktor.routing.RouteSelectorEvaluation
import io.ktor.routing.RoutingResolveContext
import com.templatePJ.auth.verify
import com.templatePJ.auth.verifyAdmin
import com.templatePJ.exception.response.UnauthorizedException

fun Route.adminRoute(callback: Route.() -> Unit): Route {
    val routeWithAuthenticate = this.createChild(object : RouteSelector(1.0) {
        override fun evaluate(context: RoutingResolveContext, segmentIndex: Int): RouteSelectorEvaluation =
            RouteSelectorEvaluation.Constant
    })

    routeWithAuthenticate.intercept(ApplicationCallPipeline.Call) {
        if (verifyAdmin()) proceed() else throw UnauthorizedException("")
    }

    callback(routeWithAuthenticate)

    return routeWithAuthenticate
}

