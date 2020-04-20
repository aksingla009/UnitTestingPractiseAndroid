package com.aman.unittestingpractiseandroid.java.unitTestingInAndroid.example14;

import android.app.Activity;

public class MyActivity extends Activity {
    private int mCount;

    @Override
    protected void onStart() {
        super.onStart();
        mCount++;
    }

    public int getCount() {
        return mCount;
    }
}
