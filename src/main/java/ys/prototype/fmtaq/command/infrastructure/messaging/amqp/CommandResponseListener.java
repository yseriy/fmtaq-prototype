package ys.prototype.fmtaq.command.infrastructure.messaging.amqp;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import ys.prototype.fmtaq.command.domain.FmtaqException;

import java.util.HashMap;
import java.util.Map;

@Component
public class CommandResponseListener {

    private final static String VERSION_HEADER_NAME = "version";
    private final static String DEFAULT_VERSION = "v1";
    private final AmqpTransportLogger logger;
    private final Map<String, CommandResponseHandler> versionHandlers = new HashMap<>();

    public CommandResponseListener(AmqpTransportLogger logger) {
        this.logger = logger;
    }

    @RabbitListener(queues = "${app.transport.amqp.queue.response.name}")
    public void processResponse(Message message) {
        try {
            tryProcessResponse(message);
        } catch (FmtaqException e) {
            logger.logFmtaqException(e, message);
        } catch (Exception e) {
            logger.logException(e, message);
        }
    }

    private void tryProcessResponse(Message message) {
        checkMessage(message);
        logAndHandleResponse(message);
    }

    private void checkMessage(Message message) {
        assert message != null : "amqp message cannot be null";
        assert message.getBody() != null : "amqp message body cannot be null";
        assert message.getMessageProperties() != null : "amqp message properties cannot be null";
        assert message.getMessageProperties().getHeaders() != null : "amqp message headers cannot be null";
    }

    private void logAndHandleResponse(Message message) {
        long startTime = System.nanoTime();
        handleResponse(message);
        long elapsedTime = (System.nanoTime() - startTime);
        logger.logCommandResponseAccessOk(message, elapsedTime);
    }

    private void handleResponse(Message message) {
        CommandResponseHandler commandResponseHandler = getVersionHandler(message);
        commandResponseHandler.handle(message);
    }

    private CommandResponseHandler getVersionHandler(Message message) {
        String version = getVersion(message);
        CommandResponseHandler commandResponseHandler = versionHandlers.get(version);

        if (commandResponseHandler == null) {
            throw unsupportedTransportVersion(version);
        }

        return commandResponseHandler;
    }

    private String getVersion(Message message) {
        Map<String, Object> headers = message.getMessageProperties().getHeaders();
        String version = (String) headers.get(VERSION_HEADER_NAME);

        if (version == null) {
            version = DEFAULT_VERSION;
        }

        return version;
    }

    private FmtaqException unsupportedTransportVersion(String version) {
        return new FmtaqException(AmqpTransportErrorList.UNSUPPORTED_TRANSPORT_VERSION).set("version", version);
    }

    public void addVersionHandler(String version, CommandResponseHandler commandResponseHandler) {
        versionHandlers.put(version, commandResponseHandler);
    }
}
