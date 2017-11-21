package ys.prototype.fmtaq.command.domain;

public enum FmtaqErrorList implements FmtaqError {
    UNKNOWN_COMMAND_RESPONSE_STATUS(106),
    EMPTY_COMMAND_SET(108),
    CANNOT_FIND_FIRST_COMMAND(109),
    EMPTY_INCOMING_COMMAND_LIST(110),
    ILLEGAL_COMMAND_COUNTER_VALUE(111);

    private final Integer code;

    FmtaqErrorList(Integer code) {
        this.code = code;
    }

    @Override
    public Integer getCode() {
        return code;
    }

    @Override
    public String getCategory() {
        return this.getClass().getSimpleName();
    }

    @Override
    public String getErrorMessage() {
        return this.toString();
    }
}