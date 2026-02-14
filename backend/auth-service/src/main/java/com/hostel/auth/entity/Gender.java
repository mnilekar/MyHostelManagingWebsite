package com.hostel.auth.entity;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum Gender {
    MALE,
    FEMALE,
    NOT_PREFERRED;

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
    }
}
