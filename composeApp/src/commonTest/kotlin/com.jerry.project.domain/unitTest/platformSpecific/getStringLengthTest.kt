package com.jerry.project.domain.unitTest.platformSpecific

import com.jerry.project.domain.platformSpecific.getStringLength
import kotlin.test.Test
import kotlin.test.assertEquals

class GetStringLengthTest {

    @Test
    fun testBasicStringLength() {
        assertEquals(5, getStringLength("12345"))
        assertEquals(0, getStringLength(""))
        assertEquals(11, getStringLength("Hello World"))
    }
}