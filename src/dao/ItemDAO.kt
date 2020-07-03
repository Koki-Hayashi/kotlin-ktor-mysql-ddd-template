package com.templatePJ.dao

import com.templatePJ.database.IncrementalIdWithFriendlyIdTable
import com.templatePJ.database.IntEntityBase
import com.templatePJ.database.IntEntityClassBase
import com.templatePJ.domain.Item
import org.jetbrains.exposed.dao.id.EntityID

object Items : IncrementalIdWithFriendlyIdTable(
    name = "items",
    friendlyIdIndexName = "idx_items_friendly_id"
) {
    val name = varchar("name", 30)
    val user = reference("user_id", Users)
}

class ItemDAO(id: EntityID<Int>) : IntEntityBase(id, Items) {
    companion object : IntEntityClassBase<ItemDAO>(Items)

    var friendlyId by Items.friendlyId
    var name by Items.name
    var user by UserDAO referencedOn Items.user

    fun toDomain(): Item {
        return Item(
            id = this.friendlyId,
            name = this.name,
            userId = this.user.friendlyId
        )
    }
}
