package com.templatePJ.routes.api.v1.users.dto.response

import com.fasterxml.jackson.annotation.JsonProperty
import com.templatePJ.domain.User

data class GetUserDto(
    @JsonProperty("user")
    val user: UserDto
) {
    companion object {
        fun from(user: User): GetUserDto {
            return GetUserDto(
                user = UserDto.from(user)
            )
        }
    }
}

