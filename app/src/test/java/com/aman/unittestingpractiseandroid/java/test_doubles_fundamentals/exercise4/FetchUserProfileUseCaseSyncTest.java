package com.aman.unittestingpractiseandroid.java.test_doubles_fundamentals.exercise4;

import com.aman.unittestingpractiseandroid.java.test_doubles_fundamentals.example4.networking.NetworkErrorException;
import com.aman.unittestingpractiseandroid.java.test_doubles_fundamentals.exercise4.FetchUserProfileUseCaseSync.UseCaseResult;
import com.aman.unittestingpractiseandroid.java.test_doubles_fundamentals.exercise4.networking.UserProfileHttpEndpointSync;
import com.aman.unittestingpractiseandroid.java.test_doubles_fundamentals.exercise4.users.User;
import com.aman.unittestingpractiseandroid.java.test_doubles_fundamentals.exercise4.users.UsersCache;

import org.jetbrains.annotations.Nullable;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

public class FetchUserProfileUseCaseSyncTest {

    FetchUserProfileUseCaseSync SUT;
    UserProfileHttpEndPointSyncTD userProfileHttpEndPointSyncTD;
    UsersCacheTD usersCacheTD;

    private static final String USER_ID = "user_id";
    private static final String FULL_NAME = "full_name";
    private static final String IMAGE_URL = "image_url";


    @Before
    public void setUp() {
        userProfileHttpEndPointSyncTD = new UserProfileHttpEndPointSyncTD();
        usersCacheTD = new UsersCacheTD();
        SUT = new FetchUserProfileUseCaseSync(userProfileHttpEndPointSyncTD, usersCacheTD);
    }

    @Test
    public void fetchUserProfileSync_success_userIdPassedToEndPoint() {
        SUT.fetchUserProfileSync(USER_ID);
        assertThat(userProfileHttpEndPointSyncTD.mUserId, is(USER_ID));
    }

    @Test
    public void fetchUserProfileSync_success_userCached() {
        SUT.fetchUserProfileSync(USER_ID);
        assertThat(userProfileHttpEndPointSyncTD.mUserId, is(USER_ID));
        User user = usersCacheTD.getUser(USER_ID);
        assertThat(user.getUserId(), is(USER_ID));
        assertThat(user.getFullName(), is(FULL_NAME));
        assertThat(user.getImageUrl(), is(IMAGE_URL));
    }

    @Test
    public void fetchUserProfileSync_authError_userNotCached() {
        userProfileHttpEndPointSyncTD.isAuthError = true;
        SUT.fetchUserProfileSync(USER_ID);
        assertThat(usersCacheTD.getUser(USER_ID), is(nullValue()));
    }

    @Test
    public void fetchUserProfileSync_serverError_userNotCached() {
        userProfileHttpEndPointSyncTD.isServerError = true;
        SUT.fetchUserProfileSync(USER_ID);
        assertThat(usersCacheTD.getUser(USER_ID), is(nullValue()));
    }

    @Test
    public void fetchUserProfileSync_generalError_userNotCached() {
        userProfileHttpEndPointSyncTD.isGeneralError = true;
        SUT.fetchUserProfileSync(USER_ID);
        assertThat(usersCacheTD.getUser(USER_ID), is(nullValue()));
    }

    //User is Cached now look for what is returned by Endpoint result
    @Test
    public void fetchUserProfileSync_success_successReturnedINEndPointResult(){
        UseCaseResult result =  SUT.fetchUserProfileSync(USER_ID);
        assertThat(result,is(UseCaseResult.SUCCESS));
    }
    @Test
    public void fetchUserProfileSync_authError_failureReturnedINEndPointResult(){
        userProfileHttpEndPointSyncTD.isAuthError = true;
        UseCaseResult result =  SUT.fetchUserProfileSync(USER_ID);
        assertThat(result,is(UseCaseResult.FAILURE));
    }
    @Test
    public void fetchUserProfileSync_serverError_failureReturnedINEndPointResult(){
        userProfileHttpEndPointSyncTD.isServerError = true;
        UseCaseResult result =  SUT.fetchUserProfileSync(USER_ID);
        assertThat(result,is(UseCaseResult.FAILURE));
    }
    @Test
    public void fetchUserProfileSync_generalError_failureReturnedINEndPointResult(){
        userProfileHttpEndPointSyncTD.isGeneralError = true;
        UseCaseResult result =  SUT.fetchUserProfileSync(USER_ID);
        assertThat(result,is(UseCaseResult.FAILURE));
    }
    @Test
    public void fetchUserProfileSync_networkError_failureReturnedINEndPointResult(){
        userProfileHttpEndPointSyncTD.isNetworkError = true;
        UseCaseResult result =  SUT.fetchUserProfileSync(USER_ID);
        assertThat(result,is(UseCaseResult.NETWORK_ERROR));
    }

    //------------------------------------------------------------------------------------//
    //Helper classes
    private static class UserProfileHttpEndPointSyncTD implements UserProfileHttpEndpointSync {
        public String mUserId = "";
        public boolean isAuthError;
        public boolean isServerError;
        public boolean isGeneralError;
        public boolean isNetworkError;

        @Override
        public EndpointResult getUserProfile(String userId) throws NetworkErrorException {
            mUserId = userId;
            if (isAuthError) {
                return new EndpointResult(EndpointResultStatus.AUTH_ERROR, "", "", "");
            } else if (isServerError) {
                return new EndpointResult(EndpointResultStatus.SERVER_ERROR, "", "", "");
            } else if (isGeneralError) {
                return new EndpointResult(EndpointResultStatus.GENERAL_ERROR, "", "", "");
            } else if(isNetworkError){
                throw new NetworkErrorException();
            }else {
                return new EndpointResult(EndpointResultStatus.SUCCESS, mUserId, FULL_NAME, IMAGE_URL);
            }

        }
    }

    private static class UsersCacheTD implements UsersCache {

        private List<User> mUsers = new ArrayList<>(1);

        @Override
        public void cacheUser(User user) {
            User existingUser = getUser(user.getUserId());
            if (existingUser != null) {
                mUsers.remove(existingUser);
            }
            mUsers.add(user);
        }

        @Override
        @Nullable
        public User getUser(String userId) {
            for (User user : mUsers) {
                if (user.getUserId().equals(userId)) {
                    return user;
                }
            }
            return null;
        }
    }

}