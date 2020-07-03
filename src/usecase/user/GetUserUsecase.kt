package com.templatePJ.usecase.user

import com.templatePJ.domain.User
import com.templatePJ.exception.response.ValidationException
import com.templatePJ.repository.UserRepository
import org.jetbrains.exposed.sql.transactions.transaction
import org.koin.core.KoinComponent
import org.koin.core.inject

class GetUserUseCase : KoinComponent {

    private val userRepository: UserRepository by inject()

    fun run(id: String): User {
        if (id.isEmpty()) throw ValidationException("id must be specified.")

        return transaction { userRepository.findByFriendlyId(id).toDomain() }
    }

}