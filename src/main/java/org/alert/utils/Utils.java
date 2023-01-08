package org.alert.utils;

import org.apache.commons.lang3.RandomStringUtils;

public class Utils {
    public static double elapsed(long start) {
        long end = System.currentTimeMillis();
        return (end - start) / 1000.0d;
    }

    // Function to convert camel case
    // string to snake case string
    public static String camelToSnake(String str) {
        // Regular Expression
        String regex = "([a-z])([A-Z]+)";

        // Replacement string
        String replacement = "$1_$2";

        // Replace the given regex
        // with replacement string
        // and convert it to lower case.
        str = str
                .replaceAll(
                        regex, replacement)
                .toLowerCase();

        // return string
        return str;
    }

    public static String randomNumeric(int length) {
        return RandomStringUtils.randomNumeric(length);
    }

}
