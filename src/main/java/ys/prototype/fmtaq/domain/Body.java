package ys.prototype.fmtaq.domain;

import lombok.Data;

@Data
public class Body {
    private final String value;

    @Override
    public String toString() {
        return value;
    }
}
