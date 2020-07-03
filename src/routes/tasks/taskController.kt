package com.templatePJ.routes.tasks

import com.templatePJ.tasks.db.SetupTask
import com.templatePJ.tasks.db.seed.SeedTask
import io.ktor.application.call
import io.ktor.response.respondText
import io.ktor.routing.Route
import io.ktor.routing.post
import org.koin.ktor.ext.inject

fun Route.taskRoute() {

    val setupTask: SetupTask by inject()
    val seedTask: SeedTask by inject()

    post("db/setup") {

        setupTask.run()

        call.respondText { "db setup done" }
    }

    post("/db/seed") {
        seedTask.run()

        call.respondText { "seeding done" }
    }
}