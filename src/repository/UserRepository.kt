package com.templatePJ.repository

import com.templatePJ.dao.UserDAO
import com.templatePJ.dao.Users
import com.templatePJ.util.UUIDUtils


class UserRepository : FriendlyIdRepositoryBase<UserDAO>(Users, UserDAO.Companion) {
    fun save(name: String): UserDAO {
        return UserDAO.new {
            this.friendlyId = UUIDUtils.generateFriendlyId()
            this.name = name
        }
    }
}