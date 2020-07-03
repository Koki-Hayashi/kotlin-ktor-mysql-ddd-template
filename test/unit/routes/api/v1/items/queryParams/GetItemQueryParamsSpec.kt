package unit.routes.api.v1.items.queryParams

import com.templatePJ.routes.api.v1.items.queryParams.GetItemQueryParams
import io.kotlintest.shouldBe
import io.kotlintest.specs.DescribeSpec

class GetItemQueryParamsSpec : DescribeSpec({
    describe("noParameterAssigned") {

        context("name is null") {
            val name = null
            val params = GetItemQueryParams(name = name)

            it("should return true") {
                params.noParameterAssigned() shouldBe true
            }
        }

        context("name has some value") {

            val name = "some name"
            val params = GetItemQueryParams(name = name)

            it("should return false") {
                params.noParameterAssigned() shouldBe false
            }
        }

    }
})
