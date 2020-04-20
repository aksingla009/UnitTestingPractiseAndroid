package com.aman.unittestingpractiseandroid.java.unitTestingInAndroid.example12;

import android.content.Context;

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
public class StringRetrieverTest {
    //region Constants----------------------------------------------------
    public static final int ID = 10;
    public static final String STRING = "string";
    //endregion Constants-------------------------------------------------

    //region Helper Fields------------------------------------------------
    @Mock
    Context mContextMock;
    //endregion Helper Fields---------------------------------------------

    private StringRetriever SUT;

    @Before
    public void setup() {
        SUT = new StringRetriever(mContextMock);

    }

    @Test
    public void getString_correctParameterPassedToContext() throws Exception {
        // Arrange
        // Act
        SUT.getString(ID);
        // Assert
        verify(mContextMock).getString(ID);
    }

    @Test
    public void getString_correctResultReturned() throws Exception {
        // Arrange
        when(mContextMock.getString(ID)).thenReturn(STRING);
        // Act
        String result = SUT.getString(ID);
        // Assert
        assertThat(result, is(STRING));
    }

    //region Helper Methods-------------------------------------------------

    //endregion Helper Methods----------------------------------------------

    //region Helper Classes-------------------------------------------------

    //endregion Helper Classes----------------------------------------------
}