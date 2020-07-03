package com.templatePJ

import com.fasterxml.jackson.databind.SerializationFeature
import io.ktor.application.Application
import io.ktor.application.install
import io.ktor.features.*
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpMethod
import io.ktor.jackson.jackson
import io.ktor.locations.Locations
import io.ktor.request.path
import io.ktor.routing.routing
import com.templatePJ.init.Initializer
import com.templatePJ.koin.KoinModules
import com.templatePJ.routes.api.api
import com.templatePJ.routes.custom.adminRoute
import com.templatePJ.routes.custom.authRoute
import com.templatePJ.routes.error.handle
import com.templatePJ.routes.fallback
import com.templatePJ.routes.ping
import com.templatePJ.routes.tasks.taskRoute
import org.koin.ktor.ext.Koin
import org.slf4j.event.Level


fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)


@Suppress("unused") // Referenced in application.conf
@kotlin.jvm.JvmOverloads
fun Application.module(testing: Boolean = false) {
    // Start DI container must be first
    install(Koin) {
        modules(KoinModules.modules())
    }

    Initializer.run(testing) // initializations should be here.

    install(Locations)

    install(Compression) {
        gzip {
            priority = 1.0
        }
        deflate {
            priority = 10.0
            minimumSize(1024) // condition
        }
    }

    install(CallLogging) {
        level = Level.INFO
        filter { call -> call.request.path().startsWith("/") }
    }

    install(CORS) {
        method(HttpMethod.Options)
        method(HttpMethod.Put)
        method(HttpMethod.Delete)
        method(HttpMethod.Patch)
        header(HttpHeaders.Authorization)
        allowCredentials = true
        anyHost() // @TODO: Don't do this in production if possible. Try to limit it.
    }

    install(ContentNegotiation) {
        jackson {
            enable(SerializationFeature.INDENT_OUTPUT)
        }
    }

    routing {
        ping()
        authRoute {
            api()
        }

        adminRoute {
            taskRoute()
        }

        fallback()
    }

    install(StatusPages) {
        handle()
    }

}


