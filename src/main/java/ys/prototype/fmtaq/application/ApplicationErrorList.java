package ys.prototype.fmtaq.application;

import ys.prototype.fmtaq.domain.FmtaqError;

public enum ApplicationErrorList implements FmtaqError {
    COMMAND_NOT_FOUND(207),
    UNKNOWN_TASK_TYPE(209);

    private final Integer code;

    ApplicationErrorList(Integer code) {
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
