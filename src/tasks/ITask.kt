package com.templatePJ.tasks

import org.koin.core.KoinComponent

interface ITask: KoinComponent {
    fun run(): Unit
}