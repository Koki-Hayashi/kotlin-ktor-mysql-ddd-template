package e2e

import database.TestDatabase
import io.ktor.server.testing.TestApplicationEngine
import io.mockk.unmockkAll
import org.koin.core.context.loadKoinModules
import org.koin.core.context.unloadKoinModules
import org.koin.test.KoinTest
import org.koin.test.inject

/*
 * All end to end test related settings are summarized here.
 */
object E2ESetUp : KoinTest {

    fun setUp(): TestApplicationEngine {
        setupBeforeStartApp()

        // start app
        val engine = TestEngine.init()

        setupAfterStartApp()

        return engine
    }

    private fun setupBeforeStartApp() {
        mockUps()
    }

    private fun mockUps() {
        // mockks up here
    }

    private fun setupAfterStartApp() {
        // override Koin modules for e2e test
        koinOverride()

        // setup test databases
        TestDatabase.setup()
        TestDatabase.cleanUp()
    }

    private fun koinOverride() {
        KoinE2EModules.modules().also {
            unloadKoinModules(it)
            loadKoinModules(it)
        }
    }

    fun cleanUp() {
        unmockkAll()

        // databases clean up
        TestDatabase.cleanUp()
        
        // clean up app
        TestEngine.cleanUp()
    }

}

