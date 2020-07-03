package e2e

import io.kotlintest.*
import io.kotlintest.specs.BehaviorSpec
import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode
import io.ktor.server.testing.handleRequest
import org.koin.test.KoinTest

class PingSpec : BehaviorSpec(), KoinTest {

    private val engine = TestEngine.init()

    override fun afterSpecClass(spec: Spec, results: Map<TestCase, TestResult>) {
        TestEngine.cleanUp()
    }

    init {
        given("application is running") {
            `when`("GET: /ping") {
                then("should return message") {
                    with(engine) {
                        handleRequest(HttpMethod.Get, "/ping").apply {
                            response.status() shouldBe HttpStatusCode.OK

                            val content = response.content ?: fail("No content ws responded")
                            content shouldBe "I'm alive"
                        }
                    }
                }
            }
        }
    }

}
