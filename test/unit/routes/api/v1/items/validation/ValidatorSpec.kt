package unit.routes.api.v1.items.validation

import com.templatePJ.consts.ITEM_NAME_MAX_LENGTH
import com.templatePJ.routes.api.v1.items.dto.request.SaveItemDto
import com.templatePJ.routes.api.v1.items.validation.validate
import getRandomString
import io.kotlintest.matchers.collections.shouldContainAll
import io.kotlintest.specs.DescribeSpec

class ValidatorSpec : DescribeSpec({
    describe("validate for SaveItemDto") {

        context("empty user id and name") {

            val userId = ""
            val name = ""
            val dto = SaveItemDto(userId = userId, name = name)

            it("should return error messages") {
                validate(dto) shouldContainAll listOf("userId is missing", "name is missing")
            }
        }

        context("name length is same as max length limit") {

            val userId = "userId"
            val maxLimitLengthName = getRandomString(ITEM_NAME_MAX_LENGTH)

            val dto = SaveItemDto(userId = userId, name = maxLimitLengthName)

            it("should return empty list") {
                validate(dto) shouldContainAll emptyList()
            }
        }

        context("too long name") {

            val userId = "userId"
            val tooLongName = getRandomString(ITEM_NAME_MAX_LENGTH + 1)

            val dto = SaveItemDto(userId = userId, name = tooLongName)

            it("should return empty list") {
                validate(dto) shouldContainAll listOf("name length should be less than or equal to 50")
            }
        }

        context("valid user id and name") {

            val userId = "userId"
            val name = "name"
            val dto = SaveItemDto(userId = userId, name = name)

            it("should return empty list") {
                validate(dto) shouldContainAll emptyList()
            }
        }
    }
})

