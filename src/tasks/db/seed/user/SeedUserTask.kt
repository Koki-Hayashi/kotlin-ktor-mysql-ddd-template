package com.templatePJ.tasks.db.seed.user

import com.templatePJ.dao.UserDAO
import com.templatePJ.repository.ItemRepository
import com.templatePJ.repository.UserRepository
import com.templatePJ.tasks.db.seed.ISeedTask
import com.templatePJ.util.UUIDUtils
import mu.KotlinLogging
import org.jetbrains.exposed.sql.transactions.transaction
import org.koin.core.inject
import java.io.BufferedReader
import java.io.FileReader

class SeedUserTask : ISeedTask {

    private val logger = KotlinLogging.logger {}
    private val userRepository: UserRepository by inject()
    private val itemRepository: ItemRepository by inject()

    override fun run() {
        logger.info { "seeding users..." }

        val reader = UserReader(BufferedReader(FileReader("./src/tasks/db/seed/user/seed.csv")))
        transaction {
            reader.lines().forEach {
                UserDAO.new {
                    friendlyId = UUIDUtils.generateFriendlyId()
                    name = it.name
                }
            }
        }

        logger.info { "done" }
    }
}