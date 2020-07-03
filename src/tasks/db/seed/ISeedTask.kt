package com.templatePJ.tasks.db.seed

import org.koin.core.KoinComponent

interface ISeedTask: KoinComponent {
    fun run(): Unit
}