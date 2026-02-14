package com.hostel.auth.entity;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum Gender {
    MALE,
    FEMALE,
    NOT_PREFERRED;
    MALE("Male"),
    FEMALE("Female"),
    NOT_PREFERRED("NotPreferred");

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

        String normalized = value.trim().replace('-', '_').replace(' ', '_').toUpperCase();
        if ("NOTPREFERRED".equals(normalized)) {
            normalized = "NOT_PREFERRED";
        }
        return Gender.valueOf(normalized);
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
