package ys.prototype.fmtaq.domain;

import lombok.Data;

@Data
public class Address {
    private final String value;

    @Override
    public String toString() {
        return value;
    }
}
