package com.aman.unittestingpractiseandroid.java.testing_fundamentals.example2

import com.aman.unittestingpractiseandroid.java.testing_fundamentals.example2.StringReverser
import org.junit.Before
import org.junit.Test

import org.hamcrest.CoreMatchers.`is`

import org.junit.Assert.assertThat

class StringReverserTestKotlin {
    var SUT : StringReverser? = null

    @Before
    fun setUp() {
        SUT =
            StringReverser()
    }

    @Test
    fun reverse_emptyString_emptyStringReturned() {
        val str = SUT?.reverse("")
        assertThat(str,`is`(""))
    }

    @Test
    fun reverse_singleCharacter_singleCharacterReturned(){
        val str = SUT?.reverse("A")
        assertThat(str,`is`("A"))
    }

    @Test
    fun reverse_longString_reversedLongStringReturned() {
        val str = SUT?.reverse("Amandeep Singla")
        assertThat(str,`is`("algniS peednamA"))
    }
}