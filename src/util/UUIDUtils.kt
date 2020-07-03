package com.templatePJ.util

import java.util.*

class UUIDUtils {
    companion object {
        fun generateFriendlyId(): String = UUID.randomUUID().toString().replace("-", "").slice(0..19)
    }
}