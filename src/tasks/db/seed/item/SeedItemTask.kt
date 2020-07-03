package com.templatePJ.tasks.db.seed.item

import com.templatePJ.repository.ItemRepository
import com.templatePJ.repository.UserRepository
import com.templatePJ.tasks.db.seed.ISeedTask
import mu.KotlinLogging
import org.jetbrains.exposed.sql.transactions.transaction
import org.koin.core.inject
import java.io.BufferedReader
import java.io.FileReader

class SeedItemTask : ISeedTask {

    private val logger = KotlinLogging.logger {}
    private val itemRepository: ItemRepository by inject()
    private val userRepository: UserRepository by inject()

    override fun run() {
        logger.info { "seeding items..." }

        val reader = ItemReader(BufferedReader(FileReader("./src/tasks/db/seed/item/seed.csv")))

        transaction {
            reader.lines().forEach {
                val user = userRepository.findById(it.userId)
                itemRepository.save(user, it.name)
            }
        }

        logger.info { "done" }
    }
}