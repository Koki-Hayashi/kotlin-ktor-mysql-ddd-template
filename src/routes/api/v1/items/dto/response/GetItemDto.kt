package com.templatePJ.routes.api.v1.items.dto.response

import com.fasterxml.jackson.annotation.JsonProperty
import com.templatePJ.domain.Item

data class GetItemDto(
    @JsonProperty("item")
    val item: ItemDto
) {
    companion object {
        fun from(item: Item): GetItemDto {
            return GetItemDto(item = ItemDto.from(item))
        }
    }
}


