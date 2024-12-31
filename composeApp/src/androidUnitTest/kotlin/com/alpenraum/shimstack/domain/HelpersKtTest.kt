package com.alpenraum.shimstack.domain

import org.junit.Test
import kotlin.test.assertEquals

class HelpersKtTest {
    @Test
    fun `test runWithErrorHandling`() {
        val testCases =
            mapOf(
                Pair(true, true) to true,
                Pair(false, true) to true,
                Pair(true, false) to false,
                Pair(false, false) to true
            )

        testCases.forEach { (input, expected) ->

            val actual = shouldThrowException(input.first, input.second)

            assertEquals(expected, actual)
        }
    }
}