package com.aman.unittestingpractiseandroid.java.mockito_fundamentals.template_of_class;

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
public class DemoTestTemplateTest {
    //region Constants----------------------------------------------------

    //endregion Constants-------------------------------------------------

    //region Helper Fields------------------------------------------------

    //endregion Helper Fields---------------------------------------------

    private DemoTestTemplate SUT;

    @Before
    public void setup() {
        SUT = new DemoTestTemplate();

    }

    @Test
    public void isValidString() throws Exception {
        //Arrange

        //Act

        //Assert

    }

    //region Helper Methods-------------------------------------------------

    //endregion Helper Methods----------------------------------------------

    //region Helper Classes-------------------------------------------------

    //endregion Helper Classes----------------------------------------------
}