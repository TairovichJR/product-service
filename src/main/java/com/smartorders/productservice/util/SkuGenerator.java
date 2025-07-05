package com.smartorders.productservice.util;

import java.util.UUID;

public class SkuGenerator {

    private SkuGenerator() {
        // Private constructor to prevent instantiation
    }

    public static String generateSku(String productTitle, String categoryName) {
        // Clean and get prefix from title (3 chars)
        String cleanTitle = productTitle.replaceAll("[^A-Za-z0-9]", "").toUpperCase();
        String titlePrefix = cleanTitle.length() >= 3 ? cleanTitle.substring(0, 3) : cleanTitle;

        // Clean and get prefix from category name (2 chars)
        String cleanCategory = categoryName.replaceAll("[^A-Za-z0-9]", "").toUpperCase();
        String catPrefix = cleanCategory.length() >= 2 ? cleanCategory.substring(0, 2) : cleanCategory;

        // Generate random part (8 chars)
        String uniquePart = UUID.randomUUID().toString().substring(0, 8).toUpperCase();

        // Format: TTT-CC-RRRRRRRR (TTT=title, CC=category, R=random)
        return String.format("%s-%s-%s", titlePrefix, catPrefix, uniquePart);
    }
}