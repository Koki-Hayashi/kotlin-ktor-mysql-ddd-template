package e2e

import io.ktor.server.testing.TestApplicationEngine
import io.ktor.server.testing.createTestEnvironment
import com.templatePJ.module
import org.koin.core.context.stopKoin

/*
 * Ktor Application runner for end to end test
 */
object TestEngine {
    fun init(): TestApplicationEngine {
        return TestApplicationEngine(createTestEnvironment()).apply {
            start(wait = false)
            application.module(testing = true) // our main module function
        }
    }

    fun cleanUp() {
        stopKoin()
    }
}