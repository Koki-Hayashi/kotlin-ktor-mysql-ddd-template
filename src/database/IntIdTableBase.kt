package com.templatePJ.database

import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.`java-time`.datetime
import java.time.LocalDateTime

open class IntIdTableBase(
    name: String = ""
) : IntIdTable(name) {
    val createdAt = datetime("created_at").default(LocalDateTime.now())
    val updatedAt = datetime("updated_at").nullable()
    val deletedAt = datetime("deleted_at").nullable()
}


