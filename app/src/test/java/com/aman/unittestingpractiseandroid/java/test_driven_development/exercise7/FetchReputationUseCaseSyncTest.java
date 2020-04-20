package com.aman.unittestingpractiseandroid.java.test_driven_development.exercise7;

import com.aman.unittestingpractiseandroid.java.test_driven_development.exercise6.networking.FetchUserHttpEndpointSync;
import com.aman.unittestingpractiseandroid.java.test_driven_development.exercise7.networking.GetReputationHttpEndpointSync;

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
public class FetchReputationUseCaseSyncTest {
    //region Constants----------------------------------------------------
    private final int REPUTATION = 45;
    //endregion Constants-------------------------------------------------

    //region Helper Fields------------------------------------------------

    //endregion Helper Fields---------------------------------------------

    private FetchReputationUseCaseSync SUT;

    @Mock
    GetReputationHttpEndpointSync getReputationHttpEndpointSyncMock;

    @Before
    public void setup() {
        SUT = new FetchReputationUseCaseSync(getReputationHttpEndpointSyncMock);
        success();

    }

    //If the server request completes successfully, then use case should indicate successful completion of the flow.

    @Test
    public void fetchReputation_success_successReturned() {
        //Arrange
        //Act
        FetchReputationUseCaseSync.UseResult result = SUT.fetchReputation();
        //Assert
        assertThat(result.getStatus(), is(FetchReputationUseCaseSync.UseResultStatus.SUCCESS));

    }

    //If the server request completes successfully, then the fetched reputation should be returned
    @Test
    public void fetchReputation_success_fetchedReputationReturned() {
        //Arrange
        //Act
        FetchReputationUseCaseSync.UseResult result = SUT.fetchReputation();
        //Assert
        assertThat(result.getReputation(), is(REPUTATION));

    }

    //If the server request fails for any reason, the use case should indicate that the flow failed.
    @Test
    public void fetchReputation_generalError_flowFailedReturned() {
        //Arrange
        generalError();
        //Act
        FetchReputationUseCaseSync.UseResult result = SUT.fetchReputation();
        //Assert
        assertThat(result.getStatus(), is(FetchReputationUseCaseSync.UseResultStatus.FAILURE));

    }

    @Test
    public void fetchReputation_networkError_flowFailedReturned() {
        //Arrange
        networkError();
        //Act
        FetchReputationUseCaseSync.UseResult result = SUT.fetchReputation();
        //Assert
        assertThat(result.getStatus(), is(FetchReputationUseCaseSync.UseResultStatus.FAILURE));

    }

    //If the server request fails for any reason, the returned reputation should be 0.
    @Test
    public void fetchReputation_generalError_ZeroReturnedInReputation() {
        //Arrange
        generalError();
        //Act
        FetchReputationUseCaseSync.UseResult result = SUT.fetchReputation();
        //Assert
        assertThat(result.getReputation(), is(0));

    }

    //If the server request fails for any reason, the returned reputation should be 0.
    @Test
    public void fetchReputation_networkError_ZeroReturnedInReputation() {
        //Arrange
        networkError();
        //Act
        FetchReputationUseCaseSync.UseResult result = SUT.fetchReputation();
        //Assert
        assertThat(result.getReputation(), is(0));

    }

    //region Helper Methods-------------------------------------------------
    private void success() {
        when(getReputationHttpEndpointSyncMock.getReputationSync())
                .thenReturn(new GetReputationHttpEndpointSync.EndpointResult(GetReputationHttpEndpointSync.EndpointStatus.SUCCESS, REPUTATION));
    }

    private void generalError() {
        when(getReputationHttpEndpointSyncMock.getReputationSync())
                .thenReturn(new GetReputationHttpEndpointSync.EndpointResult(GetReputationHttpEndpointSync.EndpointStatus.GENERAL_ERROR, 0));
    }

    private void networkError() {
        when(getReputationHttpEndpointSyncMock.getReputationSync())
                .thenReturn(new GetReputationHttpEndpointSync.EndpointResult(GetReputationHttpEndpointSync.EndpointStatus.NETWORK_ERROR, 0));
    }

    //endregion Helper Methods----------------------------------------------

    //region Helper Classes-------------------------------------------------

    //endregion Helper Classes----------------------------------------------
}