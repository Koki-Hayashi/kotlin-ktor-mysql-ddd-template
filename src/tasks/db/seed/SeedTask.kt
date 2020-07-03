package com.templatePJ.tasks.db.seed

import com.templatePJ.tasks.ITask
import com.templatePJ.tasks.db.seed.item.SeedItemTask
import com.templatePJ.tasks.db.seed.user.SeedUserTask
import mu.KotlinLogging
import org.koin.core.KoinComponent
import org.koin.core.inject

class SeedTask : ITask, KoinComponent {

    private val logger = KotlinLogging.logger {}
    private val seedUserTask: SeedUserTask by inject()
    private val seedItemTask: SeedItemTask by inject()

    override fun run() {

        logger.info { "seeding records..." }

        // order matters
        val seedTasks = listOf(seedUserTask, seedItemTask)

        seedTasks.forEach {
            it.run()
        }

        logger.info { "done." }
    }

    private fun seedItems() {
        seedItemTask
    }


}