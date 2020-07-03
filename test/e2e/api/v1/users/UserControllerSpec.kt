package e2e.api.v1.users

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.templatePJ.dao.UserDAO
import com.templatePJ.dao.Users
import com.templatePJ.routes.api.v1.users.dto.request.SaveUserDto
import com.templatePJ.routes.api.v1.users.dto.response.GetUserDto
import com.templatePJ.routes.api.v1.users.dto.response.UserDto
import e2e.E2ESetUp
import io.kotlintest.*
import io.kotlintest.extensions.TopLevelTest
import io.kotlintest.specs.BehaviorSpec
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode
import io.ktor.server.testing.handleRequest
import io.ktor.server.testing.setBody
import org.jetbrains.exposed.sql.transactions.transaction
import org.koin.test.KoinTest

// test settings
object TestUsers {
    val ID1 = "id1"
    val NAME1 = "name1"
    val USER1 = GetUserDto(user = UserDto(id = ID1, name = NAME1, items = listOf()))

    val ID2 = "id2"
    val NAME2 = "name2"
    val USER2 = GetUserDto(user = UserDto(id = ID2, name = NAME2, items = listOf()))

}

class UserControllerSpec : BehaviorSpec(), KoinTest {

    private val engine = E2ESetUp.setUp()

    override fun beforeSpecClass(spec: Spec, tests: List<TopLevelTest>) {
        transaction {
            UserDAO.new {
                this.friendlyId = TestUsers.ID1
                this.name = TestUsers.NAME1
            }

            UserDAO.new {
                this.friendlyId = TestUsers.ID2
                this.name = TestUsers.NAME2
            }
        }
    }

    override fun afterSpecClass(spec: Spec, results: Map<TestCase, TestResult>) {
        E2ESetUp.cleanUp()
    }

    override fun testCaseOrder() = TestCaseOrder.Sequential

    init {
        given("there are some users") {
            `when`("GET: /api/v1/users/${TestUsers.ID1}") {
                then("should return that user") {
                    with(engine) {
                        handleRequest(HttpMethod.Get, "/api/v1/users/${TestUsers.ID1}").apply {
                            response.status() shouldBe HttpStatusCode.OK

                            val content = response.content ?: fail("No content was responded")
                            val actual = jacksonObjectMapper().readValue<GetUserDto>(content)
                            actual shouldBe TestUsers.USER1
                        }
                    }
                }
            }

            // save case should be placed as the last one
            `when`("POST:/api/v1/users") {
                then("should save and return that user") {
                    with(engine) {

                        val newName = "name3"
                        val reqBody = jacksonObjectMapper().writeValueAsString(
                            SaveUserDto(
                                name = newName
                            )
                        )
                        handleRequest(HttpMethod.Post, "/api/v1/users") {
                            addHeader(HttpHeaders.ContentType, ContentType.Application.Json.toString())
                            setBody(
                                reqBody
                            )
                        }
                            .apply {
                                response.status() shouldBe HttpStatusCode.Created

                                val content = response.content ?: fail("No content was responded")
                                val actual = jacksonObjectMapper().readValue<GetUserDto>(content)
                                actual.user.name shouldBe newName

                                transaction {
                                    UserDAO.find { Users.friendlyId eq actual.user.id }.first().also {
                                        it.friendlyId shouldBe actual.user.id
                                        it.name shouldBe actual.user.name
                                    }
                                }
                            }
                    }
                }
            }
        }
    }
}
