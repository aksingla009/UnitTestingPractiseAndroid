package com.aman.unittestingpractiseandroid.java.test_driven_development.exercise6;

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
public class FetchUserUseCaseImplementationTest {
    //region Constants----------------------------------------------------

    //endregion Constants-------------------------------------------------

    //region Helper Fields------------------------------------------------

    //endregion Helper Fields---------------------------------------------

    private FetchUserUseCaseImplementation SUT;

    @Before
    public void setup() {
        SUT = new FetchUserUseCaseImplementation();

    }

    //region Helper Methods-------------------------------------------------

    //endregion Helper Methods----------------------------------------------

    //region Helper Classes-------------------------------------------------

    //endregion Helper Classes----------------------------------------------
}