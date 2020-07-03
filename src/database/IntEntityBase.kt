package com.templatePJ.database

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.id.EntityID
import java.time.LocalDateTime


abstract class IntEntityBase(id: EntityID<Int>, table: IntIdTableBase) : IntEntity(id) {
    val createdAt by table.createdAt
    var updatedAt by table.updatedAt
    var deletedAt by table.deletedAt

    override fun delete() { // logical delete by default
        deletedAt = LocalDateTime.now()
    }

    fun physicalDelete() {
        super.delete()
    }

}

