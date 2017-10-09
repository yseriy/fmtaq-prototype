package ys.prototype.fmtaq.exception;

import java.util.ArrayList;
import java.util.List;

public class FmtaqException extends RuntimeException {

    private final static String EMPTY_STRING = "";
    private final ErrorCode errorCode;
    private final List<String> properties = new ArrayList<>();

    public FmtaqException(ErrorCode errorCode) {
        super(errorCode.toString());
        this.errorCode = errorCode;
    }

    public FmtaqException(ErrorCode errorCode, Throwable cause) {
        super(errorCode.toString(), cause);
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
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
