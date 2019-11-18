package com.cloudurable.stringparse.internal;

import com.cloudurable.stringparse.StringToken;

import java.util.Objects;

public class StringTokenOk implements StringToken {
    private final int start;
    private final int end;

    public StringTokenOk(int start, int stop) {
        this.start = start;
        this.end = stop;
    }

    public int getStart() {
        return start;
    }

    public int getEnd() {
        return end;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StringTokenOk that = (StringTokenOk) o;
        return start == that.start &&
                end == that.end;
    }

    @Override
    public int hashCode() {
        return Objects.hash(start, end);
    }

    @Override
    public String toString() {
        return "StringToken{" +
                "start=" + start +
                ", stop=" + end +
                '}';
    }
}
