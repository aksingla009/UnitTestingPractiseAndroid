package com.aman.unittestingpractiseandroid.java.mockito_fundamentals.exercise5.eventbus;

import com.aman.unittestingpractiseandroid.java.mockito_fundamentals.exercise5.users.User;

public class UserDetailsChangedEvent {

    private final User mUser;

    public UserDetailsChangedEvent(User user) {
        mUser = user;
    }

    public User getUser() {
        return mUser;
    }
}
