package e2e.api.v1.items

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.templatePJ.consts.ITEM_NAME_MAX_LENGTH
import com.templatePJ.dao.ItemDAO
import com.templatePJ.dao.Items
import com.templatePJ.dao.UserDAO
import com.templatePJ.routes.api.v1.items.dto.request.SaveItemDto
import com.templatePJ.routes.api.v1.items.dto.response.GetItemDto
import com.templatePJ.routes.api.v1.items.dto.response.GetItemsDto
import com.templatePJ.routes.api.v1.items.dto.response.ItemDto
import com.templatePJ.routes.error.ErrorDto
import e2e.E2ESetUp
import io.kotlintest.*
import io.kotlintest.extensions.TopLevelTest
import io.kotlintest.matchers.collections.shouldContainAll
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
    val ID1 = "userId1"
    val NAME1 = "name1"

    val ID2 = "userId2"
    val NAME2 = "name2"
}

object TestItems {
    val ID1 = "id1"
    val NAME1 = "name1"
    val USER_ID1 = TestUsers.ID1
    val ITEM_DTO1 = ItemDto(id = ID1, name = NAME1, userId = USER_ID1)
    val GET_ITEM_DTO1 = GetItemDto(item = ITEM_DTO1)

    val ID2 = "id2"
    val NAME2 = "name2"
    val USER_ID2 = TestUsers.ID2
    val ITEM_DTO2 = ItemDto(id = ID2, name = NAME2, userId = USER_ID2)
    val ITEM2 = GetItemDto(item = ITEM_DTO2)

}

class ItemControllerSpec : BehaviorSpec(), KoinTest {

    private val engine = E2ESetUp.setUp()

    override fun beforeSpecClass(spec: Spec, tests: List<TopLevelTest>) {

        val user1 = transaction {
            UserDAO.new {
                this.friendlyId = TestUsers.ID1
                this.name = TestUsers.NAME1
            }
        }
        val user2 = transaction {

            UserDAO.new {
                this.friendlyId = TestUsers.ID2
                this.name = TestUsers.NAME2
            }
        }

        transaction {
            ItemDAO.new {
                friendlyId = unit.repository.Items.ID1
                name = unit.repository.Items.NAME1
                user = user1
            }
        }

        transaction {
            ItemDAO.new {
                friendlyId = unit.repository.Items.ID2
                name = unit.repository.Items.NAME2
                user = user2
            }
        }


    }

    override fun afterSpecClass(spec: Spec, results: Map<TestCase, TestResult>) {
        E2ESetUp.cleanUp()
    }

    override fun testCaseOrder() = TestCaseOrder.Sequential

    init {
        given("there are some items") {
            `when`("GET: /api/v1/items") {
                then("should return items") {
                    with(engine) {
                        handleRequest(HttpMethod.Get, "/api/v1/items").apply {
                            response.status() shouldBe HttpStatusCode.OK

                            val content = response.content ?: fail("No content was responded")
                            val actual = jacksonObjectMapper().readValue<GetItemsDto>(content)
                            actual.items shouldContainAll (listOf(TestItems.ITEM_DTO1, TestItems.ITEM_DTO2))
                        }
                    }
                }
            }

            `when`("GET: /api/v1/items/${TestItems.ID1}") {
                then("should return that item") {
                    with(engine) {
                        handleRequest(HttpMethod.Get, "/api/v1/items/${TestItems.ID1}").apply {
                            response.status() shouldBe HttpStatusCode.OK

                            val content = response.content ?: fail("No content was responded")
                            val actual = jacksonObjectMapper().readValue<GetItemDto>(content)
                            actual shouldBe TestItems.GET_ITEM_DTO1
                        }
                    }
                }
            }

            `when`("GET:/api/v1/items?name=${TestItems.NAME1}") {
                then("should return that item") {
                    with(engine) {
                        handleRequest(HttpMethod.Get, "/api/v1/items?name=${TestItems.NAME1}").apply {
                            response.status() shouldBe HttpStatusCode.OK

                            val content = response.content ?: fail("No content was responded")
                            val actual = jacksonObjectMapper().readValue<GetItemsDto>(content)
                            actual.items shouldContainAll listOf(TestItems.ITEM_DTO1)
                        }
                    }
                }
            }

            `when`("GET:/api/v1/items/?userId=${TestItems.USER_ID1}&name=TOO_LONG_ITEM_NAME") {
                then("should fail") {
                    with(engine) {

                        val tooLongItemName = "a".repeat(ITEM_NAME_MAX_LENGTH + 1)
                        handleRequest(
                            HttpMethod.Get,
                            "/api/v1/items/?userId=${TestItems.USER_ID1}&name=${tooLongItemName}"
                        ).apply {
                            response.status() shouldBe HttpStatusCode.BadRequest

                            val content = response.content ?: fail("No content was responded")
                            val actual = jacksonObjectMapper().readValue<ErrorDto>(content)
                            actual.message shouldBe "name length should be less than or equal to 50"
                        }
                    }
                }
            }

            // save case should be placed as the last one
            `when`("POST:/api/v1/items") {
                then("should save and return that item") {
                    with(engine) {

                        val newName = "name3"
                        val newUserId = TestUsers.ID1
                        val reqBody = jacksonObjectMapper().writeValueAsString(
                            SaveItemDto(
                                name = newName,
                                userId = newUserId
                            )
                        )
                        handleRequest(HttpMethod.Post, "/api/v1/items") {
                            addHeader(HttpHeaders.ContentType, ContentType.Application.Json.toString())
                            setBody(
                                reqBody
                            )
                        }
                            .apply {
                                response.status() shouldBe HttpStatusCode.Created

                                val content = response.content ?: fail("No content was responded")
                                val actual = jacksonObjectMapper().readValue<GetItemDto>(content)
                                actual.item.name shouldBe newName
                                actual.item.userId shouldBe newUserId

                                transaction {
                                    ItemDAO.find { Items.friendlyId eq actual.item.id }.first().also {
                                        it.friendlyId shouldBe actual.item.id
                                        it.name shouldBe actual.item.name
                                        it.user.friendlyId shouldBe actual.item.userId
                                    }
                                }
                            }
                    }
                }
            }

            `when`("POST:/api/v1/items with too long item name") {
                then("should fail") {
                    with(engine) {

                        val tooLongItemName = "a".repeat(ITEM_NAME_MAX_LENGTH + 1)
                        val newUserId = TestUsers.ID1
                        val reqBody = jacksonObjectMapper().writeValueAsString(
                            SaveItemDto(
                                name = tooLongItemName,
                                userId = newUserId
                            )
                        )
                        handleRequest(HttpMethod.Post, "/api/v1/items") {
                            addHeader(HttpHeaders.ContentType, ContentType.Application.Json.toString())
                            setBody(
                                reqBody
                            )
                        }
                            .apply {
                                response.status() shouldBe HttpStatusCode.BadRequest

                                val content = response.content ?: fail("No content was responded")
                                val actual = jacksonObjectMapper().readValue<ErrorDto>(content)
                                actual.message shouldBe "name length should be less than or equal to 50"
                            }
                    }
                }
            }
        }
    }
}
