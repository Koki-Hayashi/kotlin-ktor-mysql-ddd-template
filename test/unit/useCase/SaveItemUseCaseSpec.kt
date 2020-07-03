package unit.useCase

import com.templatePJ.dao.ItemDAO
import com.templatePJ.dao.UserDAO
import com.templatePJ.exception.response.ResourceNotFoundException
import com.templatePJ.repository.ItemRepository
import com.templatePJ.repository.UserRepository
import com.templatePJ.usecase.item.SaveItemUseCase
import database.TestDatabase
import io.kotlintest.Spec
import io.kotlintest.matchers.instanceOf
import io.kotlintest.should
import io.kotlintest.shouldBe
import io.kotlintest.shouldThrow
import io.kotlintest.specs.DescribeSpec
import org.jetbrains.exposed.sql.transactions.transaction
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.inject

class SaveItemUseCaseSpec : DescribeSpec(), KoinTest {

    private val saveItemUseCase: SaveItemUseCase by inject()

    override fun beforeSpec(spec: Spec) {
        startKoin {
            modules(listOf(module {
                single { SaveItemUseCase() }
                single { ItemRepository() }
                single { UserRepository() }
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

    override fun afterSpec(spec: Spec) {
        stopKoin()
    }

    init {
        describe("save")
        {

            context("user doesn't exist") {
                val name = "some name"
                val userId = "some user id"

                it("fails") {
                    shouldThrow<ResourceNotFoundException> {
                        transaction {
                            saveItemUseCase.run(name = name, userId = userId)
                        }
                    }.also {
                        it should instanceOf(ResourceNotFoundException::class)
                    }
                }
            }

            context("with a given name") {
                it("succeeds to save a new item") {
                    val newUserId = "new user id"
                    transaction {
                        UserDAO.new {
                            friendlyId = newUserId
                            name = "new user name"
                        }
                    }

                    val name = "new item"
                    val actual = transaction {
                        saveItemUseCase.run(userId = newUserId, name = name)
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