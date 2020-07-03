package com.templatePJ.usecase.item

import com.templatePJ.exception.response.ValidationException
import com.templatePJ.domain.Item
import com.templatePJ.repository.ItemRepository
import com.templatePJ.repository.UserRepository
import org.jetbrains.exposed.sql.transactions.transaction
import org.koin.core.KoinComponent
import org.koin.core.inject

class SaveItemUseCase : KoinComponent {

    private val itemRepository: ItemRepository by inject()
    private val userRepository: UserRepository by inject()

    fun run(userId: String, name: String): Item {
        if (userId.isEmpty()) throw ValidationException("userId must be specified")
        if (name.isEmpty()) throw ValidationException("name must be specified")

        return transaction {
            itemRepository.save(
                user = userRepository.findByFriendlyId(userId),
                name = name
            ).toDomain()
        }
    }

}