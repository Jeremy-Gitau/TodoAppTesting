package com.jerry.project.domain.platformSpecific

import org.junit.Test
import kotlin.test.assertEquals

class GetStringLengthTest{


    @Test
    fun getLength_OfString(){

        val getString = getStringLength("name")

        assertEquals(4,getString)
    }

}