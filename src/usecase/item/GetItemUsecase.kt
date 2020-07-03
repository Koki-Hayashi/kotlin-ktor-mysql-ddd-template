package com.templatePJ.usecase.item

import com.templatePJ.domain.Item
import com.templatePJ.exception.response.ValidationException
import com.templatePJ.repository.ItemRepository
import org.jetbrains.exposed.sql.transactions.transaction
import org.koin.core.KoinComponent
import org.koin.core.inject

class GetItemUseCase : KoinComponent {

    private val itemRepository: ItemRepository by inject()

    fun run(id: String): Item {
        if (id.isEmpty()) throw ValidationException("id must be specified")

        return transaction { itemRepository.findByFriendlyId(id).toDomain() }
    }

}