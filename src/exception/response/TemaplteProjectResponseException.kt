package com.templatePJ.exception.response

import io.ktor.http.HttpStatusCode
import com.templatePJ.exception.TemplateProjectException

open class TemaplteProjectResponseException(
    val status: HttpStatusCode,
    override val message: String
) : TemplateProjectException(message)