package com.aman.unittestingpractiseandroid.java.testing_fundamentals.example1;

import com.aman.unittestingpractiseandroid.kotlin.PositiveNumberValidator;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.core.Is.is;

public class PositiveNumberValidatorTest {

    private PositiveNumberValidator abc;

    @Before
    public void setSUT() {
        abc = new com.aman.unittestingpractiseandroid.kotlin.PositiveNumberValidator();
    }


    @Test
    public void test1() {
        boolean result = abc.isPositive(-1);
        Assert.assertThat(result, is(false));
    }

    @Test
    public void test2() {
        boolean result = abc.isPositive(1);
        Assert.assertThat(result, is(true));
    }

    @Test
    public void testPositiveNumber() {
        boolean result = abc.isPositive(1);
        Assert.assertEquals(result, true);
    }
}
