package com.templatePJ.routes.api.v1.items

import com.templatePJ.routes.api.error.errorResponseIfNotEmpty
import com.templatePJ.routes.api.v1.items.dto.request.SaveItemDto
import com.templatePJ.routes.api.v1.items.dto.response.GetItemDto
import com.templatePJ.routes.api.v1.items.dto.response.GetItemsDto
import com.templatePJ.routes.api.v1.items.queryParams.GetItemQueryParams
import com.templatePJ.routes.api.v1.items.validation.validate
import com.templatePJ.routes.error.ErrorDto
import com.templatePJ.usecase.item.GetItemByParamsUseCase
import com.templatePJ.usecase.item.GetItemUseCase
import com.templatePJ.usecase.item.GetItemsUseCase
import com.templatePJ.usecase.item.SaveItemUseCase
import io.ktor.application.call
import io.ktor.http.HttpStatusCode
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.routing.Route
import io.ktor.routing.get
import io.ktor.routing.post
import org.koin.ktor.ext.inject

fun Route.itemRoute() {

    val getItemsUseCase: GetItemsUseCase by inject()
    val getItemUseCase: GetItemUseCase by inject()
    val saveItemUseCase: SaveItemUseCase by inject()
    val getItemByQueriesUseCase: GetItemByParamsUseCase by inject()

    get("/items") {
        val queryParams = call.request.queryParameters
        val getItemQueryParams = GetItemQueryParams(queryParams)

        when (val result = validateAndSwitch(getItemQueryParams)) {
            is NO_QUERY_PARAMS -> {
                call.respond(HttpStatusCode.OK, GetItemsDto.from(getItemsUseCase.run()))
            }

            is VALIDATION_FAIL -> {
                call.respond(
                    HttpStatusCode.BadRequest,
                    ErrorDto(result.errorMessages.joinToString(separator = ", "))
                )
            }

            is HAS_VALID_PARAMS -> {
                result.queryParams.let {
                    call.respond(
                        HttpStatusCode.OK,
                        GetItemsDto.from(
                            getItemByQueriesUseCase.run(
                                name = it.name!! // already validated
                            )
                        )
                    )
                }
            }
        }
    }

    get("items/{id}") {
        val id = call.parameters["id"].toString()

        call.respond(
            HttpStatusCode.OK,
            GetItemDto.from(getItemUseCase.run(id))
        )
    }

    post("/items") {
        val dto = call.receive<SaveItemDto>()

        validate(dto).also {
            errorResponseIfNotEmpty(it, call)
        }

        call.respond(
            HttpStatusCode.Created,
            GetItemDto.from(
                saveItemUseCase.run(
                    userId = dto.userId,
                    name = dto.name
                )
            )
        )
    }
}

