package com.aman.unittestingpractiseandroid.java.unitTestingInAndroid.example13;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.mockito.ArgumentCaptor.*;

@RunWith(MockitoJUnitRunner.class)
public class AndroidUnitTestingProblemsTest {
    //region Constants----------------------------------------------------

    //endregion Constants-------------------------------------------------

    //region Helper Fields------------------------------------------------

    //endregion Helper Fields---------------------------------------------

    private AndroidUnitTestingProblems SUT;

    @Before
    public void setup() {
        SUT = new AndroidUnitTestingProblems();

    }

    @Test
    public void testStaticApiCall() throws Exception {
        // Arrange
        // Act
        SUT.callStaticAndroidApi("");
        // Assert
        assertThat(true, is(true));
    }

    //region Helper Methods-------------------------------------------------

    //endregion Helper Methods----------------------------------------------

    //region Helper Classes-------------------------------------------------

    //endregion Helper Classes----------------------------------------------
}