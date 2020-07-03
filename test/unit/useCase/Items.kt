package unit.useCase

import com.templatePJ.domain.Item


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
        get() = listOf(
            ITEM1,
            ITEM2
        )

    val size: Int
        get() = list.size
}