package com.templatePJ.routes.api.v1.items.queryParams

import io.ktor.http.Parameters


data class GetItemQueryParams(
    val name: String?
) {
    constructor(queryParams: Parameters) : this(
        name = queryParams["name"]
    )

    fun noParameterAssigned(): Boolean {
        return this.name == null
    }
}

