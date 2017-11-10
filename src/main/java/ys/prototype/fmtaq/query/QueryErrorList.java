package ys.prototype.fmtaq.query;

import ys.prototype.fmtaq.domain.FmtaqError;

public enum QueryErrorList implements FmtaqError {
    CANNOT_GET_COLUMN_VALUE_BY_COLUMN_LABEL(501),
    CANNOT_WRITE_VALUE_TO_STREAM(502),
    CANNOT_CONVERT_TO_JSON(503);

    private final Integer code;

    QueryErrorList(Integer code) {
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
