package com.hostel.auth.entity;

public enum IdProofType {
    AADHAR_CARD("Aadhar Card"),
    VOTER_ID("VoterID"),
    PASSPORT("Passport");

    private final String dbValue;

    IdProofType(String dbValue) {
        this.dbValue = dbValue;
    }

    public String getDbValue() {
        return dbValue;
    }
}
