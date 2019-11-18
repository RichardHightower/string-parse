package com.cloudurable.stringparse.internal;

import com.cloudurable.stringparse.StringToken;

public class StringTokenError implements StringToken {

    final int start;
    final int end;
    final int errorIndex;

    final String errorMessage;

    final char[] inputChars;

    public StringTokenError(final int start, final int end, final int errorIndex,
                            final String errorMessage, final char[] inputChars) {
        this.start = start;
        this.end = end;
        this.errorIndex = errorIndex;
        this.errorMessage = errorMessage;
        this.inputChars = inputChars;
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
    public boolean wasError() {
        return true;
    }

    @Override
    public String errorMessage() {
        final StringBuilder stringBuilder = new StringBuilder(end - start * 2);
        stringBuilder.append("\n");
        stringBuilder.append(new String(inputChars, start, end-start));
        stringBuilder.append("\n");

        final int relativeIndex = errorIndex - start;

        for (int i=0; i < relativeIndex-1; i++) {
            stringBuilder.append('-');
        }
        stringBuilder.append('^');
        stringBuilder.append('\n');
        stringBuilder.append(errorMessage);
        stringBuilder.append('\n');

        return stringBuilder.toString();
    }
}
