package com.templatePJ.database

import com.templatePJ.util.getDbPassword
import com.templatePJ.util.getDbUser
import com.templatePJ.util.getJdbcUrl
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.transactions.transaction

object Database {

    private fun hikari(): HikariDataSource {
        val config = HikariConfig()
        config.driverClassName = "com.mysql.cj.jdbc.Driver"
        config.jdbcUrl = getJdbcUrl()
        config.username = getDbUser()
        config.password = getDbPassword()
        config.maximumPoolSize = 5
        config.isAutoCommit = false
        config.transactionIsolation = "TRANSACTION_REPEATABLE_READ"
        config.validate()
        return HikariDataSource(config)
    }

    fun setup() {
        Database.connect(hikari())
    }

    fun seed() {
        transaction {

        }
    }
}