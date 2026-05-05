package it.unimib.sd2026;

import org.apache.commons.lang3.StringUtils;

public class Main {
    public static void main(String[] args) {
        if (args == null || args.length == 0) {
            System.out.println(StringUtils.capitalize("Hello World!"));
            return;
        }

        for (String arg : args) {
            System.out.println(StringUtils.capitalize(arg));
        }
    }
}