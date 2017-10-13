package ys.prototype.fmtaq.domain;

import java.util.ArrayList;
import java.util.List;

public class FmtaqException extends RuntimeException {

    private final static String EMPTY_STRING = "";
    private final FmtaqError fmtaqError;
    private final List<String> properties = new ArrayList<>();

    public FmtaqException(FmtaqError fmtaqError) {
        super(fmtaqError.toString());
        this.fmtaqError = fmtaqError;
    }

    public FmtaqException(FmtaqError fmtaqError, Throwable cause) {
        super(fmtaqError.toString(), cause);
        this.fmtaqError = fmtaqError;
    }

    public FmtaqError getFmtaqError() {
        return fmtaqError;
    }

    public FmtaqException set(String name, String value) {
        properties.add(name + ": " + value);
        return this;
    }

    public String printProperties() {
        return properties.stream().reduce(this::concatenateString).orElse(EMPTY_STRING);
    }

    private String concatenateString(String s1, String s2) {
        return s1 + "\n" + s2;
    }
}
