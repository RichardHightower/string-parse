package com.cloudurable.stringparse.internal;

import java.io.FileInputStream;
import java.nio.charset.StandardCharsets;

public class Util {

    public static String readFile(final String fileName) {
        try (final FileInputStream fileInputStream = new FileInputStream(fileName)) {
            byte[] bytes = fileInputStream.readAllBytes();
            return new String(bytes, StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
