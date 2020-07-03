package com.templatePJ.routes.api.v1.items.dto.response

import com.fasterxml.jackson.annotation.JsonProperty
import com.templatePJ.domain.Item

data class GetItemsDto(
    @JsonProperty("items")
    val items: List<ItemDto>
) {
    companion object {
        fun from(items: List<Item>): GetItemsDto {
            return GetItemsDto(
                items = items.map { ItemDto.from(it) }.toList()
            )
        }
    }
}


