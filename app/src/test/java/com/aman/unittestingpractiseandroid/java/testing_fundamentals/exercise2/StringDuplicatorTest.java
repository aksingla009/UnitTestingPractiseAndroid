package com.aman.unittestingpractiseandroid.java.testing_fundamentals.exercise2;

import com.aman.unittestingpractiseandroid.java.testing_fundamentals.exercise2.StringDuplicator;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;

public class StringDuplicatorTest {

    private StringDuplicator SUT;

    @Before
    public void setUP(){
        SUT = new StringDuplicator();
    }

    @Test
    public void duplicate_emptyString_emptyStringDuplicated(){
        String str = SUT.duplicate("");
        Assert.assertThat(str,is(""));
    }

    @Test
    public void duplicate_SingleCharacterString_SameStringDuplicatedReturned(){
        String str = SUT.duplicate("A");
        Assert.assertThat(str,is("AA"));
    }

    @Test
    public void duplicate_longString_LongStringDuplicatedReturned(){
        String str = SUT.duplicate("ABC");
        Assert.assertThat(str,is("ABCABC"));
    }
}