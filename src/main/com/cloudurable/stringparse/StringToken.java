package com.cloudurable.stringparse;

public interface StringToken {
    int getStart();
    int getEnd();

    default boolean wasError() {
        return false;
    }

    default String errorMessage() {
        return "";
    }

    default int errorIndex() {
        return 0;
    }

    default String value() {
        return "";
    }
}

