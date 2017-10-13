package ys.prototype.fmtaq.application;

import ys.prototype.fmtaq.domain.FmtaqError;

public enum ApplicationErrorList implements FmtaqError {
    COMMAND_NOT_FOUND(107),
    UNKNOWN_TASK_TYPE(109);

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
