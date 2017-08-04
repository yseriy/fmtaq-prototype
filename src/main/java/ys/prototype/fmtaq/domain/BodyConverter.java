package ys.prototype.fmtaq.domain;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class BodyConverter implements AttributeConverter<Body, String> {

    @Override
    public String convertToDatabaseColumn(Body attribute) {
        return attribute.toString();
    }

    @Override
    public Body convertToEntityAttribute(String dbData) {
        return new Body(dbData);
    }
}
