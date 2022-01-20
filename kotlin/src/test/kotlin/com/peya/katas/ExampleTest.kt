package com.peya.katas

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class ExampleTest {

    @Test
    fun `a basic assertion`() {
        Assertions.assertEquals(2, 1+1)
    }

    @Test
    fun `a mock verification test`() {
        val expectedResult = "a stubbed value"
        val injectable: Injectable = mock()
        whenever(injectable.aMethod()).thenReturn(expectedResult)

        val result = injectable.aMethod()

        verify(injectable).aMethod()
        assertEquals(expectedResult, result)
    }
}

interface Injectable {
    fun aMethod() : String
}