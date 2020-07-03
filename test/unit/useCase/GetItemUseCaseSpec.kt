package unit.useCase

import com.templatePJ.dao.ItemDAO
import com.templatePJ.dao.UserDAO
import com.templatePJ.exception.response.ResourceNotFoundException
import com.templatePJ.exception.response.ValidationException
import com.templatePJ.repository.ItemRepository
import com.templatePJ.repository.UserRepository
import com.templatePJ.usecase.item.GetItemUseCase
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


class GetItemUseCaseSpec : DescribeSpec(), KoinTest {

    private val getItemUseCase: GetItemUseCase by inject()

    override fun beforeSpec(spec: Spec) {
        startKoin {
            modules(listOf(module {
                single { GetItemUseCase() }
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
        describe("run")
        {
            context("id is empty") {
                it("should throw an error") {
                    shouldThrow<ValidationException> {
                        getItemUseCase.run("")
                    }.also {
                        it should instanceOf(ValidationException::class)
                    }
                }
            }
            context("there is no item with a given id") {
                it("should throw an error") {
                    shouldThrow<ResourceNotFoundException> {
                        getItemUseCase.run("something else")
                    }.also {
                        it should instanceOf(ResourceNotFoundException::class)
                    }
                }
            }
            context("there is an item with a given id") {
                it("returns item") {
                    val item = transaction {
                        getItemUseCase.run(Items.ID1)
                    }
                    item shouldBe Items.ITEM1
                }
            }
        }
    }
}