package ys.prototype.fmtaq.exception;

public enum FmtaqErrorList implements FmtaqError {
    BAD_JSON_FORMAT(100),
    BAD_UUID_FORMAT(101),
    CANNOT_BE_CONVERTED_TO_AN_INT(102),
    CANNOT_BE_CONVERTED_TO_A_TEXT(103),
    MISSING_NODE(104),
    NO_CONTENT_TO_BIND(105);

    private final Integer code;

    FmtaqErrorList(Integer code) {
        this.code = code;
    }

    @Override
    public Integer getCode() {
        return code;
    }
}
