package unit.useCase

import com.templatePJ.dao.ItemDAO
import com.templatePJ.dao.UserDAO
import com.templatePJ.repository.ItemRepository
import com.templatePJ.repository.UserRepository
import com.templatePJ.usecase.item.GetItemsUseCase
import database.TestDatabase
import io.kotlintest.Spec
import io.kotlintest.matchers.collections.shouldContainAll
import io.kotlintest.shouldBe
import io.kotlintest.specs.DescribeSpec
import org.jetbrains.exposed.sql.transactions.transaction
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.inject


class GetItemsUseCaseSpec : DescribeSpec(), KoinTest {

    private val getItemsUseCase: GetItemsUseCase by inject()

    override fun beforeSpec(spec: Spec) {
        startKoin {
            modules(listOf(module {
                single { GetItemsUseCase() }
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
            it("returns items") {
                val actual = getItemsUseCase.run()

                actual.size shouldBe 2
                transaction {
                    actual.shouldContainAll(Items.list)
                }

            }
        }
    }
}