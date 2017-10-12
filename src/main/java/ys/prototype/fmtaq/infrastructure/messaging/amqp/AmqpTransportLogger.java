package ys.prototype.fmtaq.infrastructure.messaging.amqp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.stereotype.Component;
import ys.prototype.fmtaq.domain.task.Command;
import ys.prototype.fmtaq.exception.FmtaqException;

import java.nio.charset.Charset;

@Component
public class AmqpTransportLogger {

    private final static Charset CHARSET_UTF_8 = Charset.forName("UTF-8");
    private final Logger logger = LoggerFactory.getLogger(getClass().getName());

    void logFmtaqException(FmtaqException e, Message message) {
        String messageDebugString = message.toString();
        String messageBody = new String(message.getBody(), CHARSET_UTF_8);
        String logMessageFormat = "command response processing error:\n"
                + "input amqp message: '%s'\n"
                + "decoded message body: '%s'\n"
                + "exception string: '%s'\n"
                + "%s";
        String logMessage = String.format(logMessageFormat, messageDebugString, messageBody, e.getMessage(),
                e.printProperties());
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

    void logReceiveMessage(Message message) {
        if (logger.isInfoEnabled()) {
            String messageDebugString = message.toString();
            String messageBody = new String(message.getBody(), CHARSET_UTF_8);
            String logMessageFormat = "receive command response message:\n"
                    + "input amqp message: '%s'\n"
                    + "decoded message body: '%s'";
            String logMessage = String.format(logMessageFormat, messageDebugString, messageBody);
            logger.info(logMessage);
        }
    }

    void logHandleMessageOk() {
        if (logger.isInfoEnabled()) {
            logger.info("handle command response message - ok");
        }
    }

    void logSendCommand(Command command) {
        if (logger.isDebugEnabled()) {
            String address = command.getAddress();
            String body = command.getBody();
            String logMessageFormat = "send command:\n"
                    + "command address: '%s'\n"
                    + "command body: '%s'";
            String logMessage = String.format(logMessageFormat, address, body);
            logger.debug(logMessage);
        }
    }

    void logSendCommandOk() {
        if (logger.isDebugEnabled()) {
            logger.debug("send command - ok");
        }
    }
}
