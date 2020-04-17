package com.aman.unittestingpractiseandroid.java.testing_fundamentals.example1


import org.hamcrest.core.Is.`is`
import org.junit.Assert.assertEquals
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Test
import com.aman.unittestingpractiseandroid.kotlin.PositiveNumberValidator


class PosNumberValidatorTest2 {

    private var SUT: com.aman.unittestingpractiseandroid.kotlin.PositiveNumberValidator? = null

    @Before
    fun setUP() : Unit {
        SUT = PositiveNumberValidator()
    }

    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun addition_isCorrect1() {
        assertThat(4, `is`(2 + 2))
    }

    @Test
    fun testPositiveNumber() {
        val result: Boolean? = SUT?.isPositive(1)
        assertEquals(result, true)
    }

    @Test
    fun isPositive_NegativeNumber_FalseReturned() {
        val result: Boolean? = SUT?.isPositive(-1)
        assertThat(result, `is`(false))
    }

    @Test
    fun testZeroNumber() {
        val result: Boolean? = SUT?.isPositive(0)
        assertThat(result, `is`(true))
    }


}