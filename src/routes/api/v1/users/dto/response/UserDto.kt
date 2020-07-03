package com.templatePJ.routes.api.v1.users.dto.response

import com.fasterxml.jackson.annotation.JsonProperty
import com.templatePJ.domain.User
import com.templatePJ.routes.api.v1.items.dto.response.ItemDto

data class UserDto(
    @JsonProperty("id")
    val id: String,
    @JsonProperty("name")
    val name: String,
    @JsonProperty("items")
    val items: List<ItemDto>
    ) {
    companion object {
        fun from(user: User): UserDto {
            return UserDto(id = user.id, name = user.name, items = user.items.map { ItemDto.from(it) })
        }
    }
}


