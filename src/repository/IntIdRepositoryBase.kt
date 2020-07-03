package com.templatePJ.repository

import com.templatePJ.dao.exists
import com.templatePJ.dao.findById
import com.templatePJ.database.IntEntityBase
import com.templatePJ.database.IntEntityClassBase
import com.templatePJ.database.IntIdTableBase
import org.jetbrains.exposed.sql.deleteWhere

abstract class IntIdRepositoryBase<out IE : IntEntityBase>(
    private val table: IntIdTableBase,
    private val dao: IntEntityClassBase<IE>
) {
    fun all(): List<IE> {
        return dao.all().toList()
    }

    fun findById(id: Int): IE {
        return dao.findById(table, id)
    }

    fun exists(id: Int): Boolean {
        return dao.exists(table, id)
    }

    fun delete(id: Int) {
        table.deleteWhere { table.id eq id }
    }

}