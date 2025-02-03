package com.jerry.project.domain.platformSpecific

import org.junit.Test
import kotlin.test.assertEquals

class DesktopGetStringLengthTest{

    @Test
    fun getLengthOfStringInDesktop(){
        val getString = getStringLength("you")

        assertEquals(3, getString)
    }

}