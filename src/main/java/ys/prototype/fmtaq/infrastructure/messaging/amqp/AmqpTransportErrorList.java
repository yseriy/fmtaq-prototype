package ys.prototype.fmtaq.infrastructure.messaging.amqp;

import ys.prototype.fmtaq.domain.FmtaqError;

public enum AmqpTransportErrorList implements FmtaqError {
    BAD_JSON_FORMAT(300),
    BAD_UUID_FORMAT(301),
    CANNOT_BE_CONVERTED_TO_AN_INT(302),
    CANNOT_BE_CONVERTED_TO_A_TEXT(303),
    MISSING_NODE(304),
    NO_CONTENT_TO_BIND(305),
    CANNOT_DECLARE_QUEUE(310),
    CANNOT_SEND_COMMAND(311),
    UNSUPPORTED_TRANSPORT_VERSION(312);

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
