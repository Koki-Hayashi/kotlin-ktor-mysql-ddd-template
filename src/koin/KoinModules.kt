package com.templatePJ.koin

import com.templatePJ.repository.ItemRepository
import com.templatePJ.repository.UserRepository
import com.templatePJ.tasks.db.SetupTask
import com.templatePJ.tasks.db.seed.SeedTask
import com.templatePJ.tasks.db.seed.item.SeedItemTask
import com.templatePJ.tasks.db.seed.user.SeedUserTask
import com.templatePJ.usecase.item.GetItemByParamsUseCase
import com.templatePJ.usecase.item.GetItemUseCase
import com.templatePJ.usecase.item.SaveItemUseCase
import com.templatePJ.usecase.user.GetUserUseCase
import com.templatePJ.usecase.user.SaveUserUseCase
import org.koin.core.module.Module
import org.koin.dsl.module

object KoinModules {
    fun modules(): List<Module> = listOf(module {
        // UseCases
        single { GetItemUseCase() }
        single { GetItemUseCase() }
        single { SaveItemUseCase() }
        single { GetItemByParamsUseCase() }
        single { GetUserUseCase() }
        single { SaveUserUseCase() }

        // Repository
        single { ItemRepository() }
        single { UserRepository() }

        // Task
        single { SetupTask() }
        single { SeedTask() }
        single { SeedUserTask() }
        single { SeedItemTask() }
    })
}