package ys.prototype.fmtaq.infrastructure.messaging.amqp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.stereotype.Component;
import ys.prototype.fmtaq.domain.FmtaqError;
import ys.prototype.fmtaq.domain.FmtaqException;

import java.nio.charset.Charset;

@Component
public class AmqpTransportExceptionLogger {

    private final static Charset CHARSET_UTF_8 = Charset.forName("UTF-8");
    private final Logger logger = LoggerFactory.getLogger(getClass().getName());

    void logFmtaqException(FmtaqException e, Message message) {
        String messageDebugString = message.toString();
        String messageBody = new String(message.getBody(), CHARSET_UTF_8);
        FmtaqError fmtaqError = e.getFmtaqError();
        String logMessageFormat = "command response processing error:\n"
                + "input amqp message: '%s'\n"
                + "decoded message body: '%s'\n"
                + "exception code: '%s', exception category list: '%s', exception string: '%s'\n"
                + "%s";
        String logMessage = String.format(logMessageFormat, messageDebugString, messageBody, fmtaqError.getCode(),
                fmtaqError.getCategory(), fmtaqError.getErrorMessage(), e.printProperties());
        logger.error(logMessage, e);
    }

    void logException(Exception e, Message message) {
        String messageDebugString = message.toString();
        String messageBody = new String(message.getBody(), CHARSET_UTF_8);
        String logMessageFormat = "command response processing error:\n"
                + "input amqp message: '%s'\n"
                + "decoded message body: '%s'";
        String logMessage = String.format(logMessageFormat, messageDebugString, messageBody);
        logger.error(logMessage, e);
    }
}
