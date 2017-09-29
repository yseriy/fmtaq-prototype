package ys.prototype.fmtaq.infrastructure.messaging.amqp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import ys.prototype.fmtaq.application.CommandService;
import ys.prototype.fmtaq.application.dto.CommandResponseDTO;

import java.io.IOException;
import java.nio.charset.Charset;

@Component
public class CommandResponseListener {

    private final static String CHARSET = "UTF-8";
    private final Logger logger = LoggerFactory.getLogger(getClass().getName());
    private final CommandResponseJsonConverter commandResponseJsonConverter;
    private final CommandService commandService;

    public CommandResponseListener(CommandResponseJsonConverter commandResponseJsonConverter,
                                   CommandService commandService) {
        this.commandResponseJsonConverter = commandResponseJsonConverter;
        this.commandService = commandService;
    }

    @RabbitListener(queues = "${app.transport.amqp.queue.response.name}")
    public void processResponse(Message message) {
        try {
            convertAndProcessJsonResponse(message);
        } catch (Exception e) {
            logException(e, message);
        }
    }

    private void convertAndProcessJsonResponse(Message message) throws IOException {
        String response = new String(message.getBody(), Charset.forName(CHARSET));
        CommandResponseDTO commandResponseDTO = commandResponseJsonConverter.toDTO(response);
        commandService.handleResponse(commandResponseDTO);
    }

    private void logException(Exception e, Message message) {
        String logMessageFormat = "command response processing error:\ninput amqp message: '%s'\n" +
                "decoded message body: '%s'";
        String logMessage = String.format(logMessageFormat, message.toString(), new String(message.getBody(),
                Charset.forName(CHARSET)));
        logger.error(logMessage, e);
    }
}
