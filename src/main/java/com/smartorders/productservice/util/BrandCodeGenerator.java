package com.smartorders.productservice.util;

import java.util.Random;

public class BrandCodeGenerator {

    private static final String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final int NUMERIC_LENGTH = 6;
    private static final int ALPHA_LENGTH = 4;

    private BrandCodeGenerator(){}

    public static String generateBrandCode() {
        StringBuilder code = new StringBuilder();
        Random random = new Random();

        // Add 2 random letters
        for (int i = 0; i < ALPHA_LENGTH; i++) {
            code.append(ALPHABET.charAt(random.nextInt(ALPHABET.length())));
        }

        // Add 4 random numbers
        for (int i = 0; i < NUMERIC_LENGTH; i++) {
            code.append(random.nextInt(10));
        }
        return code.toString();
    }
}
