package com.templatePJ.dao

import com.templatePJ.database.IncrementalIdWithFriendlyIdTable
import com.templatePJ.database.IntIdTableBase
import com.templatePJ.exception.response.ResourceNotFoundException
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass

fun <IE : IntEntity> IntEntityClass<IE>.exists(table: IntIdTableBase, id: Int): Boolean {
    try {
        this.findById(table = table, id = id)
    } catch (e: ResourceNotFoundException) {
        return false
    }

    return true
}

fun <IE : IntEntity> IntEntityClass<IE>.exists(
    table: IncrementalIdWithFriendlyIdTable,
    fid: String
): Boolean {
    try {
        this.findByFriendlyId(table = table, friendlyId = fid)
    } catch (e: ResourceNotFoundException) {
        return false
    }

    return true
}

fun <IE : IntEntity> IntEntityClass<IE>.findById(
    table: IntIdTableBase,
    id: Int
): IE {
    find { table.id eq id }.also {
        if (it.empty()) {
            throw ResourceNotFoundException("${table.nameInDatabaseCase()} with id $id was not found")
        }

        return it.first()
    }
}

fun <IE : IntEntity> IntEntityClass<IE>.findByFriendlyId(
    table: IncrementalIdWithFriendlyIdTable,
    friendlyId: String
): IE {
    find { table.friendlyId eq friendlyId }.also {
        if (it.empty()) {
            throw ResourceNotFoundException("${table.nameInDatabaseCase()} with id $friendlyId was not found")
        }

        return it.first()
    }
}

/*
Note:
Below is impossible with Kotlin 1.3
https://discuss.kotlinlang.org/t/static-companion-generic-constraints/12961

fun <IE : IntEntity, T: IncrementalIdWithFriendlyIdTable> IntEntityClass<IE>.findByFriendlyId(
    table: IncrementalIdWithFriendlyIdTable,
    friendlyId: String
): IE {
    // T.friendlyId is not possible...
    find { T.friendlyId eq friendlyId }.also {
        if (it.empty()) {
            throw ResourceNotFoundException("${table.nameInDatabaseCase()} with id $friendlyId was not found")
        }

        return it.first()
    }
}
*/

