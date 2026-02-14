package com.hostel.auth.entity;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class IdProofTypeConverter implements AttributeConverter<IdProofType, String> {

    @Override
    public String convertToDatabaseColumn(IdProofType attribute) {
        if (attribute == null) {
            return null;
        }
        return attribute.getDbValue();
    }

    @Override
    public IdProofType convertToEntityAttribute(String dbData) {
        if (dbData == null) {
            return null;
        }

        for (IdProofType idProofType : IdProofType.values()) {
            if (idProofType.getDbValue().equals(dbData)) {
                return idProofType;
            }
        }

        throw new IllegalArgumentException("Unknown IdProofType db value: " + dbData);
    }
}
