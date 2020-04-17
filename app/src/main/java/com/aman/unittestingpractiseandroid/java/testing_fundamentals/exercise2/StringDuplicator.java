package com.aman.unittestingpractiseandroid.java.testing_fundamentals.exercise2;

public class StringDuplicator {

    /**
     * @return concatenation of the argument with itself e.g. "hi" -> "hihi"
     */
    public String duplicate(String string) {
        // the bug is triplication instead of duplication
//        return string + string + string;
        return string + string;
    }

}
