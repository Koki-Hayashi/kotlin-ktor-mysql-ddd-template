package com.templatePJ.routes.api.v1.items.dto.response

import com.fasterxml.jackson.annotation.JsonProperty
import com.templatePJ.domain.Item

data class ItemDto(
    @JsonProperty("id")
    val id: String,
    @JsonProperty("name")
    val name: String,
    @JsonProperty("userId")
    val userId: String
) {
    companion object {
        fun from(item: Item): ItemDto {
            return ItemDto(id = item.id, name = item.name, userId = item.userId)
        }
    }
}


