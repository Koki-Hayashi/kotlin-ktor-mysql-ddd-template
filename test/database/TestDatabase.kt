package database

import com.templatePJ.dao.Items
import com.templatePJ.dao.Users
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.deleteAll
import org.jetbrains.exposed.sql.transactions.transaction

object TestDatabase {

    private lateinit var database: Database

    private fun hikari(): HikariDataSource {
        val config = HikariConfig()
        config.driverClassName = "com.mysql.cj.jdbc.Driver"
        config.jdbcUrl = "jdbc:mysql://localhost:3307/templatepj"
        config.username = "root"
        config.password = "mypassword"
        config.maximumPoolSize = 5
        config.isAutoCommit = false
        config.transactionIsolation = "TRANSACTION_REPEATABLE_READ"
        config.validate()
        return HikariDataSource(config)
    }

    fun setup() {
        database = Database.connect(hikari())

        transaction {
            SchemaUtils.create(Users)
            SchemaUtils.create(Items)
        }
    }

    fun cleanUp() {
        truncateAllTable()
    }

    private fun truncateAllTable() {
        transaction {
            Items.deleteAll()
            Users.deleteAll()
        }
    }

}