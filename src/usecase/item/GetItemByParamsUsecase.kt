package com.templatePJ.usecase.item

import com.templatePJ.exception.response.ValidationException
import com.templatePJ.domain.Item
import com.templatePJ.repository.ItemRepository
import org.jetbrains.exposed.sql.transactions.transaction
import org.koin.core.KoinComponent
import org.koin.core.inject

class GetItemByParamsUseCase : KoinComponent {

    private val itemRepository: ItemRepository by inject()

    fun run(name: String): List<Item> {
        if (name.isEmpty()) throw ValidationException("name must be specified")

        return transaction { itemRepository.findByName(name).map { it.toDomain() } }
    }

}