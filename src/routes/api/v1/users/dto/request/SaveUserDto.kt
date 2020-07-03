package com.templatePJ.routes.api.v1.users.dto.request

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class SaveUserDto(
    @JsonProperty("name")
    val name: String
)