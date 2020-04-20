package com.aman.unittestingpractiseandroid.java.test_driven_development.exercise6;

import com.aman.unittestingpractiseandroid.java.test_driven_development.exercise6.networking.FetchUserHttpEndpointSync.EndpointResult;
import com.aman.unittestingpractiseandroid.java.test_driven_development.exercise6.networking.FetchUserHttpEndpointSync.EndpointStatus;
import com.aman.unittestingpractiseandroid.java.test_driven_development.exercise6.networking.NetworkErrorException;
import com.aman.unittestingpractiseandroid.java.test_driven_development.exercise6.networking.FetchUserHttpEndpointSync;
import com.aman.unittestingpractiseandroid.java.test_driven_development.exercise6.users.User;
import com.aman.unittestingpractiseandroid.java.test_driven_development.exercise6.users.UsersCache;

class FetchUserUseCaseImplementation implements FetchUserUseCaseSync {
    private final FetchUserHttpEndpointSync fetchUserHttpEndpointSync;
    private final UsersCache usersCache;

    FetchUserUseCaseImplementation(FetchUserHttpEndpointSync fetchUserHttpEndpointSync, UsersCache usersCache) {
        this.fetchUserHttpEndpointSync = fetchUserHttpEndpointSync;
        this.usersCache = usersCache;
    }

    @Override
    public UseCaseResult fetchUserSync(String userId) {
        User user = usersCache.getUser(userId);
        if (user != null) {
            return new UseCaseResult(Status.SUCCESS, user);
        } else {
            EndpointResult result;
            try {
                result = fetchUserHttpEndpointSync.fetchUserSync(userId);
            } catch (NetworkErrorException e) {
                return new UseCaseResult(Status.NETWORK_ERROR, null);
            }

            EndpointStatus status = result.getStatus();

            if (status == EndpointStatus.SUCCESS) {
                usersCache.cacheUser(new User(result.getUserId(), result.getUsername()));
                return new UseCaseResult(Status.SUCCESS, new User(result.getUserId(), result.getUsername()));
            } else if (status == EndpointStatus.AUTH_ERROR || status == EndpointStatus.GENERAL_ERROR) {
                return new UseCaseResult(Status.FAILURE, null);
            } else {
                throw new RuntimeException("invalid endpoint result: " + result);
            }
        }

    }
}