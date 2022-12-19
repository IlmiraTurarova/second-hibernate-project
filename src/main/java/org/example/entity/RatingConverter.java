package org.example.entity;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class RatingConverter implements AttributeConverter<Rating, String> {

    @Override
    public String convertToDatabaseColumn(Rating rating) {
        return rating.getValue();
    }

    @Override
    public Rating convertToEntityAttribute(String string) {
        Rating[] values = Rating.values();
        for (Rating value : values) {
            if (value.getValue().equals(string)) {
                return value;
            }
        }
        return null;
    }
}
