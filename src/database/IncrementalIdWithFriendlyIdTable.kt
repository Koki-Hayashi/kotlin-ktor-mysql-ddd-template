package com.templatePJ.database

import com.templatePJ.database.IntIdTableBase


open class IncrementalIdWithFriendlyIdTable(
    name: String = "",
    friendlyIdColumnName: String = "fid", // friendly_id
    friendlyIdIndexName: String
) : IntIdTableBase(name) {
    val friendlyId = varchar(friendlyIdColumnName, 20).uniqueIndex(friendlyIdIndexName)
}
