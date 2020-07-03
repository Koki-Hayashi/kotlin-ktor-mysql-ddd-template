package com.templatePJ.init

import com.templatePJ.database.Database
import mu.KotlinLogging
import org.koin.core.KoinComponent
import org.koin.core.inject

private val logger = KotlinLogging.logger {}

object Initializer : KoinComponent {


    fun run(testing: Boolean) {
        logger.info { "Server initialization starting..." }

        if (!testing) {
            // In case of E2E test, each test would like to manage db statement, so not connect here.
            Database.setup()
        }

        logger.info { "Server initialization finished!" }
    }
}