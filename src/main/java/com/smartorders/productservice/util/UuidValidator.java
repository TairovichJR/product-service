package com.smartorders.productservice.util;

import com.smartorders.productservice.exception.InvalidRequestException;

import java.util.UUID;

public class UuidValidator {
    private UuidValidator(){}

    public static void validateUuid(String value) {
        if (value == null || value.trim().isEmpty()) {
            throw new InvalidRequestException("UUID cannot be empty");
        }

        String trimmedValue = value.trim();
        if (!trimmedValue.matches("^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$")) {
            String msg = "Please provide a valid UUID in the format: xxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxxxxx";
            throw new InvalidRequestException(msg);
        }

        try {
            UUID.fromString(trimmedValue);
        } catch (IllegalArgumentException e) {
            String msg = "Please provide a valid UUID in the format: xxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxxxxx";
            throw new InvalidRequestException(msg);
        }
    }
}
