package com.templatePJ.routes.api.v1.items.dto.request

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class SaveItemDto(
    @JsonProperty("userId")
    val userId: String,

    @JsonProperty("name")
    val name: String
)