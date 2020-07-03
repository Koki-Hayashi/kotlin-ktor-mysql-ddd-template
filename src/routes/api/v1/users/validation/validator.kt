package com.templatePJ.routes.api.v1.users.validation

import com.templatePJ.consts.ITEM_NAME_MAX_LENGTH
import com.templatePJ.consts.USER_NAME_MAX_LENGTH
import com.templatePJ.routes.api.v1.users.dto.request.SaveUserDto
import com.templatePJ.routes.error.ErrorMessage

fun validate(dto: SaveUserDto): List<ErrorMessage> {
    val name = dto.name
    return listOf(
        validateName(name)
    ).map { it }
        .filter { it.isNotEmpty() }
        .toList()
}

private fun validateName(name: String): String {
    if (name.length > ITEM_NAME_MAX_LENGTH) return "name length should be less than or equal to $USER_NAME_MAX_LENGTH"
    if (name.isEmpty()) return "name is missing"

    return ""
}