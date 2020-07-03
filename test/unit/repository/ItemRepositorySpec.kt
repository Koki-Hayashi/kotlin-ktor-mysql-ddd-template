package unit.repository

import com.templatePJ.dao.ItemDAO
import com.templatePJ.dao.UserDAO
import com.templatePJ.domain.Item
import com.templatePJ.exception.response.ResourceNotFoundException
import com.templatePJ.repository.ItemRepository
import database.TestDatabase
import io.kotlintest.*
import io.kotlintest.extensions.TopLevelTest
import io.kotlintest.matchers.collections.shouldContainAll
import io.kotlintest.matchers.instanceOf
import io.kotlintest.specs.DescribeSpec
import org.jetbrains.exposed.sql.transactions.transaction
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.koin.test.KoinTest

// test settings
object Items {
    val ID1 = "id1"
    val NAME1 = "name1"
    val USER_ID1 = "userId1"
    val ITEM1 = Item(id = ID1, name = NAME1, userId = USER_ID1)

    val ID2 = "id2"
    val NAME2 = "name2"
    val USER_ID2 = "userId2"
    val ITEM2 = Item(id = ID2, name = NAME2, userId = USER_ID2)

    val list: List<Item>
        get() = listOf(ITEM1, ITEM2)

    val size: Int
        get() = list.size
}

// tests
class ItemRepositoryTest : DescribeSpec(), KoinTest {

    private val itemRepository = ItemRepository()

    override fun beforeSpecClass(spec: Spec, tests: List<TopLevelTest>) {
        startKoin {
            modules(listOf(module {
                single { ItemRepository() }
            }))
        }

        TestDatabase.setup()
        TestDatabase.cleanUp()

        val user1 = transaction {
            UserDAO.new {
                friendlyId = Items.USER_ID1
                name = "user name1"
            }
        }

        transaction {
            ItemDAO.new {
                friendlyId = Items.ID1
                name = Items.NAME1
                user = user1
            }
        }

        val user2 = transaction {
            UserDAO.new {
                friendlyId = Items.USER_ID2
                name = "user name2"
            }
        }

        transaction {
            ItemDAO.new {
                friendlyId = Items.ID2
                name = Items.NAME2
                user = user2
            }
        }
    }

    override fun afterSpecClass(spec: Spec, results: Map<TestCase, TestResult>) {
        stopKoin()
        TestDatabase.cleanUp()
    }

    override fun testCaseOrder(): TestCaseOrder? = TestCaseOrder.Sequential

    init {

        describe("getItems")
        {
            context("there are some items") {
                it("should return them") {
                    val actual = transaction {
                        itemRepository.all()
                    }

                    actual.size shouldBe 2
                    transaction {
                        actual.map { it.toDomain() }.toList().shouldContainAll(Items.list)
                    }
                }
            }
        }

        describe("getItem")
        {
            context("there is no item with a given id") {
                it("should throw an error") {
                    shouldThrow<ResourceNotFoundException> {
                        transaction {
                            itemRepository.findByFriendlyId("something else")
                        }
                    }.also {
                        it should instanceOf(ResourceNotFoundException::class)
                    }
                }
            }

            context("there is an item with a given id") {
                it("should return that item") {
                    val item = transaction {
                        itemRepository.findByFriendlyId(Items.ID1)
                    }

                    transaction {
                        item.toDomain() shouldBe Items.ITEM1
                    }
                }
            }
        }

        describe("getItemByParams")
        {
            context("there is no items with a given name") {

                it("should return empty list") {
                    val actual = transaction {
                        itemRepository.findByName(name = "something else")
                    }
                    actual.size shouldBe 0
                }
            }
            context("there is an item with a given name") {
                it("should return that item") {
                    val actual = transaction {
                        itemRepository.findByName(name = Items.NAME1)
                    }
                    actual.size shouldBe 1
                    actual.first().name shouldBe Items.NAME1
                }
            }

        }

        // registration case should be placed as the last one
        describe("save")
        {
            context("with a given name") {
                it("succeeds to save a new item") {
                    val newUserId = "new user id"
                    val newUser = transaction {
                        UserDAO.new {
                            friendlyId = newUserId
                            name = "new user name"
                        }
                    }

                    val name = "new item"
                    val actual = transaction {
                        itemRepository.save(user = newUser, name = name)
                    }

                    actual.name shouldBe name
                    transaction {
                        ItemDAO.all().toList().size shouldBe Items.size + 1
                    }
                }
            }
        }
    }
}
