package com.templatePJ.usecase.item

import com.templatePJ.domain.Item
import com.templatePJ.repository.ItemRepository
import org.jetbrains.exposed.sql.transactions.transaction
import org.koin.core.KoinComponent
import org.koin.core.inject

class GetItemsUseCase : KoinComponent {

    private val itemRepository: ItemRepository by inject()

    fun run(): List<Item> {
        return transaction { itemRepository.all().map { it.toDomain() } }
    }

}