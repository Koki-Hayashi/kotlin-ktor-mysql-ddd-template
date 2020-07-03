package unit.useCase

import com.templatePJ.dao.ItemDAO
import com.templatePJ.dao.UserDAO
import com.templatePJ.exception.response.ValidationException
import com.templatePJ.repository.ItemRepository
import com.templatePJ.repository.UserRepository
import com.templatePJ.usecase.item.GetItemByParamsUseCase
import database.TestDatabase
import io.kotlintest.Spec
import io.kotlintest.matchers.collections.shouldContainAll
import io.kotlintest.matchers.instanceOf
import io.kotlintest.should
import io.kotlintest.shouldThrow
import io.kotlintest.specs.DescribeSpec
import org.jetbrains.exposed.sql.transactions.transaction
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.inject


class GetItemsByParamUseCaseSpec : DescribeSpec(), KoinTest {

    private val getItemByParamsUseCase: GetItemByParamsUseCase by inject()

    override fun beforeSpec(spec: Spec) {
        startKoin {
            modules(listOf(module {
                single { GetItemByParamsUseCase() }
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

        describe("getByName")
        {
            context("name is empty") {
                it("should throw an error") {
                    shouldThrow<ValidationException> {
                        getItemByParamsUseCase.run("")
                    }.also {
                        it should instanceOf(ValidationException::class)
                    }
                }
            }
            context("name has value") {
                it("returns item") {
                    val item = getItemByParamsUseCase.run(name = Items.NAME1)

                    item shouldContainAll listOf(Items.ITEM1)

                }
            }
        }
    }
}