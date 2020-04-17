package com.aman.unittestingpractiseandroid.java.test_doubles_fundamentals.example4;

import com.aman.unittestingpractiseandroid.java.test_doubles_fundamentals.example4.authtoken.AuthTokenCache;
import com.aman.unittestingpractiseandroid.java.test_doubles_fundamentals.example4.eventbus.EventBusPoster;
import com.aman.unittestingpractiseandroid.java.test_doubles_fundamentals.example4.eventbus.LoggedInEvent;
import com.aman.unittestingpractiseandroid.java.test_doubles_fundamentals.example4.networking.LoginHttpEndpointSync;
import com.aman.unittestingpractiseandroid.java.test_doubles_fundamentals.example4.networking.NetworkErrorException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;

public class LoginUseCaseSyncTest {
    private LoginUseCaseSync SUT;
    private LoginHttpEndPointSyncTD loginHttpEndPointSyncTD;
    private AuthTokenCacheTD authTokenCacheTD;
    private EventBusPosterTD eventBusPosterTD;

    private static final String USER_NAME = "user_name";
    private static final String PASSWORD = "password";
    private static final String AUTH_TOKEN = "auth_token";


    @Before
    public void setUp() throws Exception {
        loginHttpEndPointSyncTD = new LoginHttpEndPointSyncTD();
        authTokenCacheTD = new AuthTokenCacheTD();
        eventBusPosterTD = new EventBusPosterTD();
        //Substitute external dependencies with test Doubles
        SUT = new LoginUseCaseSync(loginHttpEndPointSyncTD, authTokenCacheTD, eventBusPosterTD);
    }


    //HOw to choose Test Cases
    //Basically what we want that the arguments which we pass to the SUT are passed to their respective collaborators properly
    //What Do we think that LoginHttpEndPointSyncTD this behaves like
    //Test case 1 : LoginHttpEndPointSyncTD should Accept the user name and password and pass to the End Point
    @Test
    public void loginSync_success_userNameAndPasswordPassedToEndPoint() {
        SUT.loginSync(USER_NAME, PASSWORD);
        //Assert that same parameters have been passed to the end point
        Assert.assertThat(loginHttpEndPointSyncTD.mUserName, is(USER_NAME));
        Assert.assertThat(loginHttpEndPointSyncTD.mPassword, is(PASSWORD));
    }

    //Interaction with AuthTokenCacheTD
    //Test Case 2: If login Succeeds - Auth Token must be cached
    @Test
    public void loginSync_success_authTokenIsCached() {
        SUT.loginSync(USER_NAME, PASSWORD);
        Assert.assertThat(authTokenCacheTD.getAuthToken(), is(AUTH_TOKEN));
    }

    //Test Case 3: If Login Fails then Auth Token is Not Changed
    //Login can fail because of multiple reasons like AUTH_ERROR, SERVER_ERROR, GENERAL_ERROR
    @Test
    public void loginSync_authError_authTokenIsNotCached() {
        loginHttpEndPointSyncTD.isAuthError = true;
        SUT.loginSync(USER_NAME, PASSWORD);
        Assert.assertThat(authTokenCacheTD.getAuthToken(), is(""));
    }

    @Test
    public void loginSync_serverError_authTokenIsNotCached() {
        loginHttpEndPointSyncTD.isServerError = true;
        SUT.loginSync(USER_NAME, PASSWORD);
        Assert.assertThat(authTokenCacheTD.getAuthToken(), is(""));
    }

    @Test
    public void loginSync_generalError_authTokenIsNotCached() {
        loginHttpEndPointSyncTD.isGeneralError = true;
        SUT.loginSync(USER_NAME, PASSWORD);
        Assert.assertThat(authTokenCacheTD.getAuthToken(), is(""));
    }


    //Interaction with EventBusPoster
    //Test Case 4: If Login Succeeds Login Event posted to Event Bus
    @Test
    public void loginSync_success_loggedInEventPosted() {
        SUT.loginSync(USER_NAME,PASSWORD);
        Assert.assertThat(eventBusPosterTD.mEvent, is(instanceOf(LoggedInEvent.class)));
    }

    //Test Case 5: If Login Fails Failure or Error (AUTH_ERROR, SERVER_ERROR, GENERAL_ERROR)posted to event bus
    @Test
    public void loginSync_authError_noInteractionWithEventBusPoster() {
        loginHttpEndPointSyncTD.isAuthError = true;
        SUT.loginSync(USER_NAME,PASSWORD);
        Assert.assertThat(eventBusPosterTD.interactionsCount, is(0));
    }
    @Test
    public void loginSync_serverError_noInteractionWithEventBusPoster() {
        loginHttpEndPointSyncTD.isServerError = true;
        SUT.loginSync(USER_NAME,PASSWORD);
        Assert.assertThat(eventBusPosterTD.interactionsCount, is(0));
    }
    @Test
    public void loginSync_generalError_noInteractionWithEventBusPoster() {
        loginHttpEndPointSyncTD.isGeneralError = true;
        SUT.loginSync(USER_NAME,PASSWORD);
        Assert.assertThat(eventBusPosterTD.interactionsCount, is(0));
    }

    //Since all the interactions with external dependencies are covered

    //Now we wil se every time SUT should return accurate results from the Endpoint
    //Test Case 6: If Login Succeeds - SUCCESS returned
    @Test
    public void loginSync_success_successReturned(){
        LoginUseCaseSync.UseCaseResult result = SUT.loginSync(USER_NAME,PASSWORD);
        Assert.assertThat(result,is(LoginUseCaseSync.UseCaseResult.SUCCESS));
    }

    //Test Case 7 : If login Fails -- Failed returned  (AUTH_ERROR, SERVER_ERROR, GENERAL_ERROR,)
    @Test
    public void loginSync_authError_FailureReturned(){
        loginHttpEndPointSyncTD.isAuthError = true;
        LoginUseCaseSync.UseCaseResult result = SUT.loginSync(USER_NAME,PASSWORD);
        Assert.assertThat(result,is(LoginUseCaseSync.UseCaseResult.FAILURE));
    }
    @Test
    public void loginSync_serverError_FailureReturned(){
        loginHttpEndPointSyncTD.isServerError = true;
        LoginUseCaseSync.UseCaseResult result = SUT.loginSync(USER_NAME,PASSWORD);
        Assert.assertThat(result,is(LoginUseCaseSync.UseCaseResult.FAILURE));
    }
    @Test
    public void loginSync_generalError_FailureReturned(){
        loginHttpEndPointSyncTD.isGeneralError = true;
        LoginUseCaseSync.UseCaseResult result = SUT.loginSync(USER_NAME,PASSWORD);
        Assert.assertThat(result,is(LoginUseCaseSync.UseCaseResult.FAILURE));
    }
    //Test Case 8 : If login fails -- Network Error Returned because of NetworkErrorException
    @Test
    public void loginSync_networkError_NetworkErrorReturned(){
        loginHttpEndPointSyncTD.isNetworkError = true;
        LoginUseCaseSync.UseCaseResult result = SUT.loginSync(USER_NAME,PASSWORD);
        Assert.assertThat(result,is(LoginUseCaseSync.UseCaseResult.NETWORK_ERROR));
    }


    //Create Test Doubles For all the external dependencies
    //Initially all these test doubles are dumb and do nothing but only helps in instantiating  the SUT

    //Its a mock because it captures the parameters as received and then works on them
    //Its a stub because it can produce different return values
    private static class LoginHttpEndPointSyncTD implements LoginHttpEndpointSync {
        String mUserName;
        String mPassword;
        boolean isAuthError;
        boolean isServerError;
        boolean isGeneralError;
        boolean isNetworkError;

        @Override
        public EndpointResult loginSync(String username, String password) throws NetworkErrorException {
            //Turned into a mock
            this.mUserName = username;
            this.mPassword = password;
            if (isAuthError) {
                //because of this LoginHttpEndPointSyncTD behaves as Stub
                //So one Test Double can behave as both Mock, Stub and Fake at same time
                return new EndpointResult(EndpointResultStatus.AUTH_ERROR, "");
            } else if (isServerError) {
                return new EndpointResult(EndpointResultStatus.SERVER_ERROR, "");
            } else if (isGeneralError) {
                return new EndpointResult(EndpointResultStatus.GENERAL_ERROR, "");
            } else if (isNetworkError){
                throw new NetworkErrorException();
            }else {
                return new EndpointResult(EndpointResultStatus.SUCCESS, AUTH_TOKEN);
            }

        }
    }


    private static class AuthTokenCacheTD implements AuthTokenCache {
        //Turned into a Fake
        //Fake is working implementation but instead of storing auth token in cache (persistent storage)
        // its storing it in memory
        String authToken = "";

        @Override
        public void cacheAuthToken(String authToken) {
            this.authToken = authToken;

        }

        @Override
        public String getAuthToken() {
            return authToken;
        }
    }

    private static class EventBusPosterTD implements EventBusPoster {
        public Object mEvent;
        public int interactionsCount = 0;

        @Override
        public void postEvent(Object event) {
            interactionsCount++;
            mEvent = event;
        }
    }

}



