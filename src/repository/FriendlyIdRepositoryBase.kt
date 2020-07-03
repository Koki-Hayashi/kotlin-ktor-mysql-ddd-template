package com.templatePJ.repository

import com.templatePJ.dao.findByFriendlyId
import com.templatePJ.database.IncrementalIdWithFriendlyIdTable
import com.templatePJ.database.IntEntityBase
import com.templatePJ.database.IntEntityClassBase
import com.templatePJ.exception.response.ResourceNotFoundException
import org.jetbrains.exposed.sql.deleteWhere

abstract class FriendlyIdRepositoryBase<IE : IntEntityBase>(
    private val table: IncrementalIdWithFriendlyIdTable,
    private val dao: IntEntityClassBase<IE>
) : IntIdRepositoryBase<IE>(table, dao) {

    fun findByFriendlyId(fid: String): IE {
        return dao.findByFriendlyId(table, fid)
    }

    fun exists(fid: String): Boolean {
        try {
            this.findByFriendlyId(fid)
        } catch (e: ResourceNotFoundException) {
            return false
        }

        return true
    }

    fun delete(fid: String) {
        table.deleteWhere { table.friendlyId eq fid }
    }

}