package com.templatePJ.tasks.db

import com.templatePJ.dao.Items
import com.templatePJ.dao.Users
import mu.KotlinLogging
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

class SetupTask{

    private val logger = KotlinLogging.logger {}

    fun run() {
        logger.info { "creating tables..." }

        transaction {
            SchemaUtils.create(Users)
            SchemaUtils.create(Items)
        }

        logger.info { "done" }
    }
}