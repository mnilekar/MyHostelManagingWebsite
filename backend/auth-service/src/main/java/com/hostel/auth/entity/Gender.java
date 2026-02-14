package com.hostel.auth.entity;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum Gender {
    MALE("MALE"),
    FEMALE("FEMALE"),
    NOT_PREFERRED("NOT_PREFERRED");

    private final String dbValue;

    Gender(String dbValue) {
        this.dbValue = dbValue;
    }

    public String getDbValue() {
        return dbValue;
    }

    @JsonCreator
    public static Gender fromValue(String value) {
        if (value == null) {
            return null;
        }

        String normalized = value.trim();
        for (Gender gender : values()) {
            if (gender.name().equalsIgnoreCase(normalized)
                || gender.dbValue.equalsIgnoreCase(normalized)
                || gender.dbValue.replace("_", "").equalsIgnoreCase(normalized.replace("_", ""))) {
                return gender;
            }
        }

        throw new IllegalArgumentException("Invalid gender value: " + value);
    }
}
