package com.cloudurable.stringparse;

import com.cloudurable.stringparse.internal.StringTokenError;
import com.cloudurable.stringparse.internal.StringTokenOk;
import com.cloudurable.stringparse.internal.StringTokenWithValue;

public class StringParser {

    private final char startChar = 0x0020;

    public StringToken scanString(String inputString) {
        return scanChars(inputString.toCharArray(), 0, inputString.length());
    }

    public StringToken scanChars(final char[] inputChars, final int start, final int upToEnd) {

        char ch = inputChars[start];

        if (ch != '"') {
            return stringTokenError(String.format("String must start with quote: character found '%c'", ch), start, inputChars, start, upToEnd);
        }

        int index = start + 1;

        for (; index < upToEnd; index++) {
            ch = inputChars[index];

            if (ch == '"') {
                break;
            }

            if (ch >= startChar) {
                if (ch == '\\') {
                    index++;
                    if (index < upToEnd) {
                        ch = inputChars[index];
                        switch (ch) {
                            case 'n':
                            case 'r':
                            case 't':
                            case 'b':
                            case 'f':
                            case '/':
                                break;
                            case 'u':
                                if (index + 5 > upToEnd) {
                                    return stringTokenError(String.format("Bad Unicode early end %c", ch), index, inputChars, start, upToEnd);
                                }
                                index++;


                                final int endHex = index + 4;

                                for (; index < endHex; index++) {
                                    ch =  inputChars[index];
                                    if (ch >= 97 && ch <=102 ||
                                            ch >= 48 && ch <= 57 ||
                                            ch >= 65 && ch <= 70) {

                                    }else {
                                        return stringTokenError(String.format("Bad Unicode char %c", ch), index, inputChars, start, upToEnd);
                                    }
                                }
                                break;

                            default:
                                return stringTokenError(String.format("escape character not found '%c'", ch), index, inputChars, start, upToEnd);
                        }
                    }
                }

            } else {
                return stringTokenError(String.format("control character found '%c'", ch), index, inputChars, start, upToEnd);
            }

        }

        return new StringTokenOk(start, index);
    }

    public String parseString(String inputString) {
        return parseChars(inputString.toCharArray(), 0, inputString.length());
    }

    public StringToken parseCharsToToken(final char[] inputChars, final int start, final int upToEnd) {

        final StringBuilder stringBuilder = new StringBuilder(upToEnd - start);
        char ch = inputChars[start];

        if (ch != '"') {
            return stringTokenError(String.format("String must start with quote: character found '%c'", ch), start, inputChars, start, upToEnd);
        }

        int index = start + 1;
        for (; index < upToEnd; index++) {
            ch = inputChars[index];

            if (ch == '"') {
                break;
            }

            if (ch >= startChar) {
                if (ch == '\\') {
                    index++;
                    if (index < upToEnd) {
                        ch = inputChars[index];
                        switch (ch) {
                            case 'n':
                                stringBuilder.append('\n');
                                break;
                            case 'r':
                                stringBuilder.append('\r');
                                break;
                            case 't':
                                stringBuilder.append('\t');
                                break;
                            case 'u':

                                if (index + 5 > upToEnd) {
                                    return stringTokenError(String.format("Bad Unicode early end %c", ch), index, inputChars, start, upToEnd);
                                }
                                index++;

                                final int startHex = index;

                                final int endHex = index + 4;

                                for (; index < endHex; index++) {
                                    ch =  inputChars[index];
                                    if (ch >= 97 && ch <=102 ||
                                            ch >= 48 && ch <= 57 ||
                                            ch >= 65 && ch <= 70) {

                                    }else {
                                        return stringTokenError(String.format("Bad Unicode char %c", ch), index, inputChars, start, upToEnd);
                                    }
                                }

                                final var hexString = new String(inputChars, startHex, 4);
                                final char newCh = (char) Integer.parseInt(hexString, 16);
                                stringBuilder.append(newCh);
                                break;
                            case 'b':
                                stringBuilder.append('\b');
                                break;
                            case 'f':
                                stringBuilder.append('\f');
                                break;
                            case '/':
                                stringBuilder.append('/');
                                break;

                            default:
                                return stringTokenError(String.format("escape character not found '%c'", ch), index, inputChars, start, upToEnd);
                        }
                    } else return stringTokenError(String.format("escape with no char", ch), index, inputChars, start, upToEnd);

                } else {
                    stringBuilder.append(ch);
                }

            } else {
                return stringTokenError(String.format("control character found '%c'", ch), index, inputChars, start, upToEnd);
            }

        }

        return new StringTokenWithValue(start, index, stringBuilder.toString());
    }

    public String parseChars(final char[] inputChars, final int start, final int upToEnd) {
        final var stringToken = parseCharsToToken(inputChars, start, upToEnd);
        if (stringToken.wasError()) {
            throw new RuntimeException(stringToken.errorMessage());
        } else {
            return stringToken.value();
        }
    }


    private StringToken stringTokenError(final String errorMessage, final int errorIndex,
                                         final char[] inputChars,
                                         final int start,
                                         final int end) {
        return new StringTokenError(start, end, errorIndex, errorMessage, inputChars);
    }
}
