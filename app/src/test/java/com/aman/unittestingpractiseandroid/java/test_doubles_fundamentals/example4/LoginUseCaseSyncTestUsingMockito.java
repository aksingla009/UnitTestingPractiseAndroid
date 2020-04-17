package com.aman.unittestingpractiseandroid.java.test_doubles_fundamentals.example4;

import com.aman.unittestingpractiseandroid.java.test_doubles_fundamentals.example4.LoginUseCaseSync.UseCaseResult;
import com.aman.unittestingpractiseandroid.java.test_doubles_fundamentals.example4.authtoken.AuthTokenCache;
import com.aman.unittestingpractiseandroid.java.test_doubles_fundamentals.example4.eventbus.EventBusPoster;
import com.aman.unittestingpractiseandroid.java.test_doubles_fundamentals.example4.eventbus.LoggedInEvent;
import com.aman.unittestingpractiseandroid.java.test_doubles_fundamentals.example4.networking.LoginHttpEndpointSync;
import com.aman.unittestingpractiseandroid.java.test_doubles_fundamentals.example4.networking.LoginHttpEndpointSync.EndpointResult;
import com.aman.unittestingpractiseandroid.java.test_doubles_fundamentals.example4.networking.LoginHttpEndpointSync.EndpointResultStatus;
import com.aman.unittestingpractiseandroid.java.test_doubles_fundamentals.example4.networking.NetworkErrorException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.util.List;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

public class LoginUseCaseSyncTestUsingMockito {
    LoginUseCaseSync SUT;
    private static final String USER_NAME = "user_name";
    private static final String PASSWORD = "password";
    private static final String AUTH_TOKEN = "auth_token";


    //We added the Mock keyword as the postfix in object to test doubles implemented with Mockito
    //because there are operations that can be performed on them but aren't applicable on manual mocks
    //This postfix MOCK helps us distinguish between two types of test doubles
    LoginHttpEndpointSync loginHttpEndpointSyncMock;
    AuthTokenCache authTokenCacheMock;
    EventBusPoster eventBusPosterMock;

    @Before
    public void setUp() throws Exception {
        //Now we will setup the SUT with Mockito Mocks
        //To initialise Mockito mocks we need to call static method mock and pass the class that we want to mock
        loginHttpEndpointSyncMock = Mockito.mock(LoginHttpEndpointSync.class);
        authTokenCacheMock = Mockito.mock(AuthTokenCache.class);
        eventBusPosterMock = Mockito.mock(EventBusPoster.class);
        SUT = new LoginUseCaseSync(loginHttpEndpointSyncMock, authTokenCacheMock, eventBusPosterMock);

        //Use mockito's WHEN method to make loginHttpEndpointSyncMock when loginSync method is called with any String as arguments
        //We want it to return EndPointResult with status success and AuthToken
        when(loginHttpEndpointSyncMock.loginSync(any(String.class), any(String.class)))
                .thenReturn(new EndpointResult(EndpointResultStatus.SUCCESS, AUTH_TOKEN));

        //By putting this code in setUp method we ensure that the flow executed by SUT completes successfully

    }

    @Test
    public void loginSync_success_userNameAndPasswordPassedToEndPoint() throws Exception {
        ArgumentCaptor<String> ac = ArgumentCaptor.forClass(String.class);
        SUT.loginSync(USER_NAME, PASSWORD);
        //Verify is Mockito's assertion; that verifies that some method was called on a specific mock
        Mockito.verify(loginHttpEndpointSyncMock, Mockito.times(1)).loginSync(ac.capture(), ac.capture());
        //Which means that loginSync is the method that is called from loginHttpEndPointSyncMock exactly one time
        //and then we can use argument captor to capture the arguments of this method
        List<String> allCaptures = ac.getAllValues();
        // Here we are retrieving all the values which argument captor captured
        assertThat(allCaptures.get(0), is(USER_NAME));
        //here we are asserting that first value which corresponds to first argument userName is actually a USER_NAME
        assertThat(allCaptures.get(1), is(PASSWORD));
    }

    @Test
    public void loginSync_success_authTokenIsCached() {
        ArgumentCaptor<String> ac = ArgumentCaptor.forClass(String.class);
        SUT.loginSync(USER_NAME, PASSWORD);
        verify(authTokenCacheMock).cacheAuthToken(ac.capture());
        //We have not passed times(1) as its for clarity by default it means called one time
        assertThat(ac.getValue(), is(AUTH_TOKEN));
    }

    @Test
    public void loginSync_authError_authTokenIsNotCached() throws Exception {
        authError();
        ArgumentCaptor<String> ac = ArgumentCaptor.forClass(String.class);
        SUT.loginSync(USER_NAME, PASSWORD);
        verifyNoMoreInteractions(authTokenCacheMock);
    }

    @Test
    public void loginSync_serverError_authTokenIsNotCached() throws Exception {
        serverError();
        ArgumentCaptor<String> ac = ArgumentCaptor.forClass(String.class);
        SUT.loginSync(USER_NAME, PASSWORD);
        verifyNoMoreInteractions(authTokenCacheMock);
    }

    @Test
    public void loginSync_generalError_authTokenIsNotCached() throws Exception {
        generalError();
        ArgumentCaptor<String> ac = ArgumentCaptor.forClass(String.class);
        SUT.loginSync(USER_NAME, PASSWORD);
        verifyNoMoreInteractions(authTokenCacheMock);
    }


    //Interaction with EventBusPoster

    @Test
    public void loginSync_success_loggedInEventPosted() {
        ArgumentCaptor<Object> ac = ArgumentCaptor.forClass(Object.class);
        SUT.loginSync(USER_NAME, PASSWORD);
        verify(eventBusPosterMock).postEvent(ac.capture());
        assertThat(ac.getValue(), is(instanceOf(LoggedInEvent.class)));
    }

    //Test Case 5: If Login Fails Failure or Error (AUTH_ERROR, SERVER_ERROR, GENERAL_ERROR)posted to event bus
    @Test
    public void loginSync_authError_noInteractionWithEventBusPoster() throws Exception {
        authError();
        SUT.loginSync(USER_NAME, PASSWORD);
        verifyNoMoreInteractions(eventBusPosterMock);
    }

    @Test
    public void loginSync_serverError_noInteractionWithEventBusPoster() throws Exception {
        serverError();
        SUT.loginSync(USER_NAME, PASSWORD);
        verifyNoMoreInteractions(eventBusPosterMock);
    }

    @Test
    public void loginSync_generalError_noInteractionWithEventBusPoster() throws Exception {
        generalError();
        SUT.loginSync(USER_NAME, PASSWORD);
        verifyNoMoreInteractions(eventBusPosterMock);
    }


    @Test
    public void loginSync_success_successReturned() {
        UseCaseResult result = SUT.loginSync(USER_NAME, PASSWORD);
        assertThat(result, is(UseCaseResult.SUCCESS));

    }

    @Test
    public void loginSync_authError_FailureReturned() throws Exception {
        authError();
        UseCaseResult result = SUT.loginSync(USER_NAME,PASSWORD);
        assertThat(result, is(UseCaseResult.FAILURE));
    }

    @Test
    public void loginSync_serverError_FailureReturned() throws Exception {
        serverError();
        UseCaseResult result = SUT.loginSync(USER_NAME,PASSWORD);
        assertThat(result, is(UseCaseResult.FAILURE));
    }

    @Test
    public void loginSync_generalError_FailureReturned() throws Exception {
        generalError();
        UseCaseResult result = SUT.loginSync(USER_NAME,PASSWORD);
        assertThat(result, is(UseCaseResult.FAILURE));
    }

    @Test
    public void loginSync_networkError_NetworkErrorReturned() throws Exception{
        networkError();
        UseCaseResult result = SUT.loginSync(USER_NAME,PASSWORD);
        assertThat(result, is(UseCaseResult.NETWORK_ERROR));
    }



    private void authError() throws Exception {
        when(loginHttpEndpointSyncMock.loginSync(any(String.class), any(String.class)))
                .thenReturn(new EndpointResult(EndpointResultStatus.AUTH_ERROR, ""));
    }

    private void serverError() throws Exception {
        when(loginHttpEndpointSyncMock.loginSync(any(String.class), any(String.class)))
                .thenReturn(new EndpointResult(EndpointResultStatus.SERVER_ERROR, ""));
    }

    private void generalError() throws Exception {
        when(loginHttpEndpointSyncMock.loginSync(any(String.class), any(String.class)))
                .thenReturn(new EndpointResult(EndpointResultStatus.GENERAL_ERROR, ""));
    }


    private void networkError() throws Exception {
        /*when(loginHttpEndpointSyncMock.loginSync(any(String.class), any(String.class)))
                .thenThrow(new NetworkErrorException());*/
        doThrow(new NetworkErrorException())
                .when(loginHttpEndpointSyncMock).loginSync(any(String.class),any(String.class));
    }

}



