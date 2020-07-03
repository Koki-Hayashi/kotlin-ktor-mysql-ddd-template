package com.templatePJ.database

import org.jetbrains.exposed.dao.EntityChangeType
import org.jetbrains.exposed.dao.EntityHook
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.toEntity
import java.time.LocalDateTime

abstract class IntEntityClassBase<E : IntEntityBase>(table: IntIdTableBase) : IntEntityClass<E>(table) {

    init {
        EntityHook.subscribe { action ->
            if (action.changeType == EntityChangeType.Updated)
                action.toEntity(this)?.updatedAt = LocalDateTime.now()
        }
    }
}