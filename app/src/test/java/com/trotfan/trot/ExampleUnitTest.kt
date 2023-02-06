package com.trotfan.trot

import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }
}


data class User(val name: String, val age: Int, val list: String)

val user1 = User("John", 18, "Hiking")
val user2 = User("Sara", 25, "Chess")
val user3 = User("Dave", 34, "Games")

@Test
fun givenList_whenConvertToMap_thenResult() {
    val myList = listOf(user1, user2, user3)
    val myMap = myList.map { it.name to it.age }.toMap()

    assertTrue(myMap.get("John") == 18)
}