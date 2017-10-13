package ys.prototype.fmtaq.infrastructure.messaging.amqp;

import ys.prototype.fmtaq.domain.FmtaqError;

public enum AmqpTransportErrorList implements FmtaqError {
    BAD_JSON_FORMAT(100),
    BAD_UUID_FORMAT(101),
    CANNOT_BE_CONVERTED_TO_AN_INT(102),
    CANNOT_BE_CONVERTED_TO_A_TEXT(103),
    MISSING_NODE(104),
    NO_CONTENT_TO_BIND(105),
    CANNOT_DECLARE_QUEUE(110),
    CANNOT_SEND_COMMAND(111);

    private final Integer code;

    AmqpTransportErrorList(Integer code) {
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
