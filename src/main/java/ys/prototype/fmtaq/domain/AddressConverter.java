package ys.prototype.fmtaq.domain;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class AddressConverter implements AttributeConverter<Address, String> {
    @Override
    public String convertToDatabaseColumn(Address attribute) {
        return attribute.toString();
    }

    @Override
    public Address convertToEntityAttribute(String dbData) {
        return new Address(dbData);
    }
}
