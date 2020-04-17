package com.aman.unittestingpractiseandroid.java.test_doubles_fundamentals.example4.authtoken;

public interface AuthTokenCache {

    void cacheAuthToken(String authToken);

    String getAuthToken();
}
