package com.templatePJ.routes.api.v1.users

import com.templatePJ.routes.api.error.errorResponseIfNotEmpty
import com.templatePJ.routes.api.v1.users.dto.request.SaveUserDto
import com.templatePJ.routes.api.v1.users.dto.response.GetUserDto
import com.templatePJ.routes.api.v1.users.validation.validate
import com.templatePJ.usecase.user.GetUserUseCase
import com.templatePJ.usecase.user.SaveUserUseCase
import io.ktor.application.call
import io.ktor.http.HttpStatusCode
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.routing.Route
import io.ktor.routing.get
import io.ktor.routing.post
import org.koin.ktor.ext.inject

fun Route.userRoute() {

    val getUserUseCase: GetUserUseCase by inject()
    val saveUserUseCase: SaveUserUseCase by inject()

    get("users/{id}") {
        val id = call.parameters["id"].toString()

        call.respond(
            HttpStatusCode.OK,
            GetUserDto.from(getUserUseCase.run(id))
        )
    }

    post("/users") {
        val dto = call.receive<SaveUserDto>()

        validate(dto).also {
            errorResponseIfNotEmpty(it, call)
        }

        call.respond(
            HttpStatusCode.Created,
            GetUserDto.from(saveUserUseCase.run(dto.name))
        )

    }

}

