package com.jerry.project.domain.platformSpecific

import kotlin.test.Test
import kotlin.test.assertEquals

class GetStringLengthWasmJsTest {

    @Test
    fun getLengthOfStringInWeb() {
        val getString = getStringLength("12345")

        assertEquals(5, getString)
    }
}