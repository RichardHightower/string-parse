package com.cloudurable.stringparse.internal;

import com.cloudurable.stringparse.StringToken;

public class StringTokenWithValue implements StringToken {

    private final int start;
    private final int end;
    private final String value;

    public StringTokenWithValue(int start, int end, String value) {
        this.start = start;
        this.end = end;
        this.value = value;
    }


    @Override
    public int getStart() {
        return start;
    }

    @Override
    public int getEnd() {
        return end;
    }


    @Override
    public String value() {
        return value;
    }
}
