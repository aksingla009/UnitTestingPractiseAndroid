package com.aman.unittestingpractiseandroid.java.unitTestingInAndroid.example14;

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
public class MyActivityTest {
    //region Constants----------------------------------------------------

    //endregion Constants-------------------------------------------------

    //region Helper Fields------------------------------------------------

    //endregion Helper Fields---------------------------------------------

    private MyActivity SUT;

    @Before
    public void setup() {
        SUT = new MyActivity();

    }

    @Test
    public void onStart_incrementsCountByOne() throws Exception {
        // Arrange
        // Act
        SUT.onStart();
        int result = SUT.getCount();
        // Assert
        assertThat(result, is(1));
    }
    //region Helper Methods-------------------------------------------------

    //endregion Helper Methods----------------------------------------------

    //region Helper Classes-------------------------------------------------

    //endregion Helper Classes----------------------------------------------
}