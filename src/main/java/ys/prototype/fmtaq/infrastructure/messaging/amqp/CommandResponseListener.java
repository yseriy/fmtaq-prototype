package ys.prototype.fmtaq.infrastructure.messaging.amqp;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import ys.prototype.fmtaq.application.CommandService;
import ys.prototype.fmtaq.application.dto.CommandResponseDTO;
import ys.prototype.fmtaq.domain.FmtaqException;

import java.nio.charset.Charset;

@Component
public class CommandResponseListener {

    private final static Charset CHARSET_UTF_8 = Charset.forName("UTF-8");
    private final CommandResponseJsonConverter commandResponseJsonConverter;
    private final CommandService commandService;
    private final AmqpTransportLogger logger;

    public CommandResponseListener(CommandResponseJsonConverter commandResponseJsonConverter,
                                   CommandService commandService, AmqpTransportLogger logger) {
        this.commandResponseJsonConverter = commandResponseJsonConverter;
        this.commandService = commandService;
        this.logger = logger;
    }

    @RabbitListener(queues = "${app.transport.amqp.queue.response.name}")
    public void processResponse(Message message) {
        try {
            logProcessResponse(message);
        } catch (FmtaqException e) {
            logger.logFmtaqException(e, message);
        } catch (Exception e) {
            logger.logException(e, message);
        }
    }

    private void logProcessResponse(Message message) {
        long startTime = System.nanoTime();
        processJsonResponse(message);
        long elapsedTime = (System.nanoTime() - startTime);
        logger.logCommandResponseAccessOk(message, elapsedTime);
    }

    private void processJsonResponse(Message message) {
        assert message != null : "amqp message cannot be null";
        assert message.getBody() != null : "amqp message body cannot be null";

        String messageBody = new String(message.getBody(), CHARSET_UTF_8);
        CommandResponseDTO commandResponseDTO = commandResponseJsonConverter.toDTO(messageBody);
        commandService.handleResponse(commandResponseDTO);
    }
}
