package com.aman.unittestingpractiseandroid.java.mockito_fundamentals.exercise5;

import com.aman.unittestingpractiseandroid.java.mockito_fundamentals.exercise5.UpdateUsernameUseCaseSync.UseCaseResult;
import com.aman.unittestingpractiseandroid.java.mockito_fundamentals.exercise5.eventbus.EventBusPoster;
import com.aman.unittestingpractiseandroid.java.mockito_fundamentals.exercise5.eventbus.UserDetailsChangedEvent;
import com.aman.unittestingpractiseandroid.java.mockito_fundamentals.exercise5.networking.NetworkErrorException;
import com.aman.unittestingpractiseandroid.java.mockito_fundamentals.exercise5.networking.UpdateUsernameHttpEndpointSync;
import com.aman.unittestingpractiseandroid.java.mockito_fundamentals.exercise5.networking.UpdateUsernameHttpEndpointSync.EndpointResult;
import com.aman.unittestingpractiseandroid.java.mockito_fundamentals.exercise5.networking.UpdateUsernameHttpEndpointSync.EndpointResultStatus;
import com.aman.unittestingpractiseandroid.java.mockito_fundamentals.exercise5.users.User;
import com.aman.unittestingpractiseandroid.java.mockito_fundamentals.exercise5.users.UsersCache;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UpdateUsernameUseCaseSyncTest {

    private static final String USER_ID = "userId";
    private static final String USERNAME = "username";

    private UpdateUsernameUseCaseSync SUT;

    @Mock
    private UpdateUsernameHttpEndpointSync updateUsernameHttpEndpointSyncMock;

    @Mock
    private UsersCache usersCacheMock;

    @Mock
    private EventBusPoster eventBusPosterMock;

    @Before
    public void setUp() throws Exception {
        /*
        Since we have annotated these variables with @Mock so we need not explicitly mock them now
        For @MOck annotation to work we need to make the test class @RunWith(MockitoJUnitRunner.class)
        updateUsernameHttpEndpointSyncMock = mock(UpdateUsernameHttpEndpointSync.class);
        usersCacheMock = mock(UsersCache.class);
        eventBusPosterMock = mock(EventBusPoster.class);*/
        SUT = new UpdateUsernameUseCaseSync(updateUsernameHttpEndpointSyncMock, usersCacheMock, eventBusPosterMock);

        success();

    }

    @Test
    public void updateUsernameSync_success_UserIdAndUserNamePassedSuccessfullyToEndPoint() throws Exception {
        ArgumentCaptor<String> ac = ArgumentCaptor.forClass(String.class);
        SUT.updateUsernameSync(USER_ID, USERNAME);
        verify(updateUsernameHttpEndpointSyncMock).updateUsername(ac.capture(), ac.capture());
        List<String> capturesList = ac.getAllValues();
        assertThat(capturesList.get(0), is(USER_ID));
        assertThat(capturesList.get(1), is(USERNAME));
    }

    /* @Test
     public void updateUsernameSync_success_UserCached() throws Exception{
         ArgumentCaptor<User> ac = ArgumentCaptor.forClass(User.class);
         SUT.updateUsernameSync(USER_ID,USERNAME);
         verify(usersCache).cacheUser(ac.capture());
         assertThat(ac.getValue(),is(instanceOf(User.class)));
         //Doing this we won't know what are the values actually cached
     }
 */
    @Test
    public void updateUsernameSync_success_UserCached() {
        ArgumentCaptor<User> ac = ArgumentCaptor.forClass(User.class);
        SUT.updateUsernameSync(USER_ID, USERNAME);
        verify(usersCacheMock).cacheUser(ac.capture());
        User user = ac.getValue();
        assertThat(user.getUserId(), is(USER_ID));
        assertThat(user.getUsername(), is(USERNAME));
    }

    @Test
    public void updateUserNameSync_authError_UserNotCached() throws Exception {
        authError();
        SUT.updateUsernameSync(USER_ID, USERNAME);
        verifyNoMoreInteractions(usersCacheMock);
    }

    @Test
    public void updateUserNameSync_serverError_UserNotCached() throws Exception {
        serverError();
        SUT.updateUsernameSync(USER_ID, USERNAME);
        verifyNoMoreInteractions(usersCacheMock);
    }


    @Test
    public void updateUserNameSync_generalError_UserNotCached() throws Exception {
        generalError();
        SUT.updateUsernameSync(USER_ID, USERNAME);
        verifyNoMoreInteractions(usersCacheMock);
    }

    @Test
    public void updateUserNameSync_success_UserDetailsChangedEvent() {
        ArgumentCaptor<UserDetailsChangedEvent> ac = ArgumentCaptor.forClass(UserDetailsChangedEvent.class);
        SUT.updateUsernameSync(USER_ID, USERNAME);
        verify(eventBusPosterMock).postEvent(ac.capture());
        assertThat(ac.getValue(), is(instanceOf(UserDetailsChangedEvent.class)));
    }

    @Test
    public void updateUserNameSync_authError_NoInteractionsWithEventBusPoster() throws Exception {
        authError();
        SUT.updateUsernameSync(USER_ID, USERNAME);
        verifyNoMoreInteractions(eventBusPosterMock);
    }

    @Test
    public void updateUserNameSync_serverError_NoInteractionsWithEventBusPoster() throws Exception {
        serverError();
        SUT.updateUsernameSync(USER_ID, USERNAME);
        verifyNoMoreInteractions(eventBusPosterMock);
    }

    @Test
    public void updateUserNameSync_generalError_NoInteractionsWithEventBusPoster() throws Exception {
        generalError();
        SUT.updateUsernameSync(USER_ID, USERNAME);
        verifyNoMoreInteractions(eventBusPosterMock);
    }

    @Test
    public void updateUserNameSync_success_UseCaseResultSuccessReturned() {
        UseCaseResult result = SUT.updateUsernameSync(USER_ID, USERNAME);
        assertThat(result, is(UseCaseResult.SUCCESS));
    }

    @Test
    public void updateUserNameSync_authError_UseCaseResultFailureReturned() throws Exception {
        authError();
        UseCaseResult result = SUT.updateUsernameSync(USER_ID, USERNAME);
        assertThat(result, is(UseCaseResult.FAILURE));
    }
    //How to Create a Test Template
    //Right CLick and select Generate option (Alt+Insert)
    //Choose Test Method and in right sub menu choose edit Template


    @Test
    public void updateUserNameSync_serverError_UseCaseResultFailureReturned() throws Exception {
        //Arrange
        serverError();
        //ACT
        UseCaseResult result = SUT.updateUsernameSync(USER_ID, USERNAME);
        //Assert
        assertThat(result, is(UseCaseResult.FAILURE));
    }

    @Test
    public void updateUserNameSync_generalError_UseCaseResultFailureReturned() throws Exception {
        generalError();
        UseCaseResult result = SUT.updateUsernameSync(USER_ID, USERNAME);
        assertThat(result, is(UseCaseResult.FAILURE));
    }

    @Test
    public void updateUserNameSync_networkError_UseCaseResultFailureReturned() throws Exception {
        networkError();
        UseCaseResult result = SUT.updateUsernameSync(USER_ID, USERNAME);
        assertThat(result, is(UseCaseResult.NETWORK_ERROR));
    }

    private void networkError() throws Exception {
        doThrow(new NetworkErrorException())
                .when(updateUsernameHttpEndpointSyncMock).updateUsername(anyString(), anyString());
    }

    private void generalError() throws Exception {
        when(updateUsernameHttpEndpointSyncMock.updateUsername(anyString(), anyString()))
                .thenReturn(new EndpointResult(EndpointResultStatus.GENERAL_ERROR, "", ""));
    }


    private void authError() throws Exception {
        when(updateUsernameHttpEndpointSyncMock.updateUsername(anyString(), anyString()))
                .thenReturn(new EndpointResult(EndpointResultStatus.AUTH_ERROR, "", ""));
    }

    private void serverError() throws Exception {
        when(updateUsernameHttpEndpointSyncMock.updateUsername(anyString(), anyString()))
                .thenReturn(new EndpointResult(EndpointResultStatus.SERVER_ERROR, "", ""));
    }


    private void success() throws Exception {
        when(updateUsernameHttpEndpointSyncMock.updateUsername(anyString(), anyString()))
                .thenReturn(new EndpointResult(EndpointResultStatus.SUCCESS, USER_ID, USERNAME));
    }

}