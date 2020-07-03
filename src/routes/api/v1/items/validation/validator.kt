package com.templatePJ.routes.api.v1.items.validation

import com.templatePJ.consts.ITEM_NAME_MAX_LENGTH
import com.templatePJ.routes.api.v1.items.dto.request.SaveItemDto
import com.templatePJ.routes.api.v1.items.queryParams.GetItemQueryParams
import com.templatePJ.routes.error.ErrorMessage

fun validate(dto: SaveItemDto): List<ErrorMessage> {
    val userId = dto.userId
    val name = dto.name
    return listOf(
        validateUserIdToSave(userId),
        validateNameToSave(name)
    ).filter { it.isNotEmpty() }
        .toList()
}

private fun validateUserIdToSave(userId: String): ErrorMessage {
    return if (userId.isEmpty()) "userId is missing" else ""
}

private fun validateNameToSave(name: String): ErrorMessage {
    if (name.length > ITEM_NAME_MAX_LENGTH) return "name length should be less than or equal to $ITEM_NAME_MAX_LENGTH"

    return if (name.isEmpty()) "name is missing" else ""
}

fun validate(getItemQueryParams: GetItemQueryParams): List<ErrorMessage> {
    val name = getItemQueryParams.name

    return listOf(
        validateNameAsParam(name)
    ).filter { it.isNotEmpty() }
        .toList()
}

private fun validateNameAsParam(name: String?): ErrorMessage {
    if (name == null) return "name is missing"

    return if (name.length > ITEM_NAME_MAX_LENGTH)
        "name length should be less than or equal to $ITEM_NAME_MAX_LENGTH"
    else ""
}
