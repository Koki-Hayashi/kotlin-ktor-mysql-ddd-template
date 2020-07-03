package com.templatePJ.repository

import com.templatePJ.dao.ItemDAO
import com.templatePJ.dao.Items
import com.templatePJ.dao.UserDAO
import com.templatePJ.util.UUIDUtils
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq


class ItemRepository : FriendlyIdRepositoryBase<ItemDAO>(Items, ItemDAO.Companion) {
    fun findByName(name: String): List<ItemDAO> {
        return ItemDAO.find(
            Items.name eq name
        ).toList()
    }

    fun save(user: UserDAO, name: String): ItemDAO {
        return ItemDAO.new {
            this.friendlyId = UUIDUtils.generateFriendlyId()
            this.name = name
            this.user = user
        }
    }
}