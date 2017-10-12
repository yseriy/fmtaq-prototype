package ys.prototype.fmtaq.exception;

public enum FmtaqErrorList implements FmtaqError {
    BAD_JSON_FORMAT(100),
    BAD_UUID_FORMAT(101),
    CANNOT_BE_CONVERTED_TO_AN_INT(102),
    CANNOT_BE_CONVERTED_TO_A_TEXT(103),
    MISSING_NODE(104),
    NO_CONTENT_TO_BIND(105),
    UNKNOWN_COMMAND_RESPONSE_STATUS(106),
    COMMAND_NOT_FOUND(107),
    EMPTY_COMMAND_SET(108),
    UNKNOWN_TASK_TYPE(109),
    CANNOT_DECLARE_QUEUE(110),
    CANNOT_SEND_COMMAND(111);

    private final Integer code;

    FmtaqErrorList(Integer code) {
        this.code = code;
    }

    @Override
    public Integer getCode() {
        return code;
    }
}
