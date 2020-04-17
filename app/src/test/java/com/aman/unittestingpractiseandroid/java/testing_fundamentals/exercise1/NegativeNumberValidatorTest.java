package com.aman.unittestingpractiseandroid.java.testing_fundamentals.exercise1;

import com.aman.unittestingpractiseandroid.java.testing_fundamentals.exercise1.NegativeNumberValidator;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.core.Is.is;

public class NegativeNumberValidatorTest {

    private NegativeNumberValidator SUT;

    @Before
    public void setSUT(){
        SUT = new NegativeNumberValidator();
    }

    @Test
    public void isNegativeNumberNegative() {
        boolean result = SUT.isNegative(-1);
        Assert.assertThat(result, is(true));
    }

    @Test
    public void isZeroNegative() {
        boolean result = SUT.isNegative(0);
        Assert.assertThat(result, is(false));
    }

    @Test
    public void isPositiveNumberNegative() {
        boolean result = SUT.isNegative(1);
        Assert.assertThat(result, is(false));
    }
}