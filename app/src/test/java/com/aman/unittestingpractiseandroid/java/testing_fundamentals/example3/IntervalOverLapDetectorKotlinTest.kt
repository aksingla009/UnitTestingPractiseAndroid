package com.aman.unittestingpractiseandroid.java.testing_fundamentals.example3

import com.aman.unittestingpractiseandroid.java.testing_fundamentals.example3.Interval
import com.aman.unittestingpractiseandroid.java.testing_fundamentals.example3.IntervalsOverlapDetector
import org.hamcrest.CoreMatchers.`is`
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class IntervalOverLapDetectorKotlinTest {

    private var SUT: IntervalsOverlapDetector? = null

    @Before
    fun setUp() {
        SUT =
            IntervalsOverlapDetector()
    }

    //Interval 1 is before Interval 2
    @Test
    fun isOverLap_interval1IsBeforeInterval2_falseReturned() {
        val interval1: Interval =
            Interval(
                1,
                3
            )
        val interval2: Interval =
            Interval(
                5,
                8
            )
        val result: Boolean? = SUT?.isOverlap(interval1, interval2)
        Assert.assertThat(result, `is`(false))

    }

    //Interval 1 overlaps Interval 2 at start
    @Test
    fun isOverLap_interval1OverlapsInterval2AtStart_trueReturned() {
        val interval1 =
            Interval(
                1,
                3
            )
        val interval2 =
            Interval(
                2,
                8
            )
        val result: Boolean? = SUT?.isOverlap(interval1, interval2)
        Assert.assertThat(result, `is`(true))
    }

    //Interval 1 is contained within Interval2
    @Test
    fun isOverLap_interval1ContainedWithINInterval2_trueReturned() {
        val interval1 =
            Interval(
                3,
                7
            )
        val interval2 =
            Interval(
                2,
                8
            )
        val result: Boolean? = SUT?.isOverlap(interval1, interval2)
        Assert.assertThat(result, `is`(true))
    }

    //Interval 2 is contained within Interval1
    @Test
    fun isOverLap_interval2ContainedWithINInterval1_trueReturned() {
        val interval1 =
            Interval(
                2,
                8
            )
        val interval2 =
            Interval(
                3,
                7
            )
        val result: Boolean? = SUT?.isOverlap(interval1, interval2)
        Assert.assertThat(result, `is`(true))
    }

    //Interval 1 overlaps Interval2 at end
    @Test
    fun isOverLap_interval1OverlapsInterval2AtEnd_trueReturned() {
        val interval1 =
            Interval(
                2,
                8
            )
        val interval2 =
            Interval(
                3,
                10
            )
        val result: Boolean? = SUT?.isOverlap(interval1, interval2)
        Assert.assertThat(result, `is`(true))
    }

    //Interval1 is after Interval2
    @Test
    fun isOverLap_interval1IsAfterInterval2_trueReturned() {
        val interval1 =
            Interval(
                11,
                15
            )
        val interval2 =
            Interval(
                3,
                10
            )
        val result: Boolean? = SUT?.isOverlap(interval1, interval2)
        Assert.assertThat(result, `is`(false))
    }

    //interval1 is before and adjacent to interval2
    @Test
    fun isOverLap_interval1IsBeforeAndAdjacentToInterval2_falseReturned() {
        val interval1 =
            Interval(
                -1,
                4
            )
        val interval2 =
            Interval(
                4,
                10
            )
        val result: Boolean? = SUT?.isOverlap(interval1, interval2)
        Assert.assertThat(result, `is`(false))
    }

    //interval1 is after and adjacent to interval2
    @Test
    fun isOverLap_interval1IsAfterAndAdjacentToInterval2_falseReturned() {
        val interval1 =
            Interval(
                10,
                14
            )
        val interval2 =
            Interval(
                0,
                10
            )
        val result: Boolean? = SUT?.isOverlap(interval1, interval2)
        Assert.assertThat(result, `is`(false))
    }
}