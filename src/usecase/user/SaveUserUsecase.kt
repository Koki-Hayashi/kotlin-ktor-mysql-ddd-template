package com.templatePJ.usecase.user

import com.templatePJ.domain.User
import com.templatePJ.exception.response.ValidationException
import com.templatePJ.repository.UserRepository
import org.jetbrains.exposed.sql.transactions.transaction
import org.koin.core.KoinComponent
import org.koin.core.inject

class SaveUserUseCase : KoinComponent {

    private val userRepository: UserRepository by inject()

    fun run(name: String): User {
        if (name.isEmpty()) throw ValidationException("name must be specified.")

        return transaction { userRepository.save(name).toDomain() }
    }


}