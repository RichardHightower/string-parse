package com.cloudurable.stringparse;

import com.cloudurable.stringparse.internal.Util;

import java.io.File;

public class Main {



    public static void main(final String[] args) {

        var parser = new StringParser();

        var file = new File("./testData/test1.dat");

        var contents = Util.readFile(file.getAbsoluteFile().toString());

        System.out.println(contents);

        var transformed = parser.parseString(contents);

        final var testString = "This is a string \n Line2 \n Line3 \n \t tab \u00fe";
        if (!transformed.equals(testString)) {
            throw new RuntimeException("bad string " + contents + "\nNOT\n" + testString);
        }

        var stringToken = parser.scanString(contents);

        var t2 = parser.parseChars(contents.toCharArray(),stringToken.getStart(), stringToken.getEnd());

        if (!transformed.equals(t2)) {
            throw new RuntimeException("bad string " + contents + "\nNOT\n" + t2);
        }

    }
}
