package com.templatePJ.routes.api.v1.items

import com.templatePJ.routes.api.v1.items.queryParams.GetItemQueryParams
import com.templatePJ.routes.api.v1.items.validation.validate
import com.templatePJ.routes.error.ErrorMessage

sealed class RESULT

object NO_QUERY_PARAMS : RESULT()
data class VALIDATION_FAIL(val errorMessages: List<ErrorMessage>) : RESULT()
data class HAS_VALID_PARAMS(val queryParams: GetItemQueryParams) : RESULT()

fun validateAndSwitch(queryParams: GetItemQueryParams): RESULT {
    if (queryParams.noParameterAssigned()) return NO_QUERY_PARAMS

    val errorMessages = validate(queryParams)
    if (errorMessages.isNotEmpty()) {
        return VALIDATION_FAIL(errorMessages)
    }

    return HAS_VALID_PARAMS(queryParams)
}