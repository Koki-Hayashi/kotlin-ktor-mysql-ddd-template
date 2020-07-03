package com.templatePJ.dao

import com.templatePJ.database.IncrementalIdWithFriendlyIdTable
import com.templatePJ.database.IntEntityBase
import com.templatePJ.database.IntEntityClassBase
import com.templatePJ.domain.User
import org.jetbrains.exposed.dao.id.EntityID

object Users : IncrementalIdWithFriendlyIdTable(
    name = "users",
    friendlyIdIndexName = "idx_users_friendly_id"
) {
    val name = varchar("name", 30)
}

class UserDAO(id: EntityID<Int>) : IntEntityBase(id, Users) {
    companion object : IntEntityClassBase<UserDAO>(Users)

    var friendlyId by Users.friendlyId
    var name by Users.name
    val items by ItemDAO referrersOn Items.user

    fun toDomain(): User {
        return User(
            id = this.friendlyId,
            name = this.name,
            items = this.items.map { it.toDomain() }
        )
    }
}