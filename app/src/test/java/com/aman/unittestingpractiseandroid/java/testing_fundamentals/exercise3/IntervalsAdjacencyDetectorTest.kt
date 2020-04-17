package com.aman.unittestingpractiseandroid.java.testing_fundamentals.exercise3

import com.aman.unittestingpractiseandroid.java.testing_fundamentals.example3.Interval
import com.aman.unittestingpractiseandroid.java.testing_fundamentals.exercise3.IntervalsAdjacencyDetector
import org.hamcrest.CoreMatchers.`is`
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class IntervalsAdjacencyDetectorTest {
    private var SUT : IntervalsAdjacencyDetector? = null

    @Before
    fun setUp(){
        SUT =
            IntervalsAdjacencyDetector()
    }

    //Interval1 is before Interval2
    @Test
    fun isAdjacent_Interval1IsBeforeInterval2_falseReturned(){
        val interval1 =
            Interval(
                -4,
                1
            )
        val interval2 =
            Interval(
                5,
                8
            )
        val result = SUT?.isAdjacent(interval1,interval2)
        Assert.assertThat(result, `is`(false))
    }

    //Interval1 is before and adjacent in start to interval2
    @Test
    fun isAdjacent_Interval1IsBeforeAndAdjacentToInterval2AtStart_trueReturned(){
        val interval1 =
            Interval(
                -4,
                1
            )
        val interval2 =
            Interval(
                1,
                8
            )
        val result = SUT?.isAdjacent(interval1,interval2)
        Assert.assertThat(result, `is`(true))
    }

    //Interval1 is overlaping in start with interval2
    @Test
    fun isAdjacent_Interval1OverlapsInterval2AtStart_falseReturned(){
        val interval1 =
            Interval(
                -4,
                3
            )
        val interval2 =
            Interval(
                1,
                8
            )
        val result = SUT?.isAdjacent(interval1,interval2)
        Assert.assertThat(result, `is`(false))
    }

    //Interval1 is contained in interval2
    @Test
    fun isAdjacent_Interval1ContainedInInterval2_falseReturned(){
        val interval1 =
            Interval(
                3,
                5
            )
        val interval2 =
            Interval(
                1,
                8
            )
        val result = SUT?.isAdjacent(interval1,interval2)
        Assert.assertThat(result, `is`(false))
    }

    //Interval2 is contained in interval1
    @Test
    fun isAdjacent_Interval2ContainedInInterval1_falseReturned(){
        val interval1 =
            Interval(
                1,
                8
            )
        val interval2 =
            Interval(
                2,
                5
            )
        val result = SUT?.isAdjacent(interval1,interval2)
        Assert.assertThat(result, `is`(false))
    }

    //Interval1 is overlaping in end with interval2
    @Test
    fun isAdjacent_Interval1OverlapsInterval2AtEnd_falseReturned(){
        val interval1 =
            Interval(
                -4,
                5
            )
        val interval2 =
            Interval(
                2,
                8
            )
        val result = SUT?.isAdjacent(interval1,interval2)
        Assert.assertThat(result, `is`(false))
    }

    //Interval1 is after and adjacent in end to interval2
    @Test
    fun isAdjacent_Interval1IsAfterAndAdjacentToInterval2AtEnd_trueReturned(){
        val interval1 =
            Interval(
                8,
                12
            )
        val interval2 =
            Interval(
                1,
                8
            )
        val result = SUT?.isAdjacent(interval1,interval2)
        Assert.assertThat(result, `is`(true))
    }

    //Interval1 is after interval2
    @Test
    fun isAdjacent_Interval1IsAfterInterval2_falseReturned(){
        val interval1 =
            Interval(
                8,
                12
            )
        val interval2 =
            Interval(
                1,
                7
            )
        val result = SUT?.isAdjacent(interval1,interval2)
        Assert.assertThat(result, `is`(false))
    }

    //Interval1 is same as interval2
    @Test
    fun isAdjacent_Interval1IsSameAsInterval2_falseReturned(){
        val interval1 =
            Interval(
                1,
                7
            )
        val interval2 =
            Interval(
                1,
                7
            )
        val result = SUT?.isAdjacent(interval1,interval2)
        Assert.assertThat(result, `is`(false))
    }
}