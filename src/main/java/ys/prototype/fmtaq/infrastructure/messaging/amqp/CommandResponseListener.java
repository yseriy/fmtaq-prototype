package ys.prototype.fmtaq.infrastructure.messaging.amqp;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import ys.prototype.fmtaq.application.CommandService;
import ys.prototype.fmtaq.application.dto.CommandResponseDTO;
import ys.prototype.fmtaq.exception.FmtaqException;

import java.nio.charset.Charset;

@Component
public class CommandResponseListener {

    private final static Charset CHARSET_UTF_8 = Charset.forName("UTF-8");
    private final AmqpTransportLogger logger;
    private final CommandResponseJsonConverter commandResponseJsonConverter;
    private final CommandService commandService;

    public CommandResponseListener(AmqpTransportLogger logger,
                                   CommandResponseJsonConverter commandResponseJsonConverter,
                                   CommandService commandService) {
        this.logger = logger;
        this.commandResponseJsonConverter = commandResponseJsonConverter;
        this.commandService = commandService;
    }

    @RabbitListener(queues = "${app.transport.amqp.queue.response.name}")
    public void processResponse(Message message) {
        try {
            logger.logReceiveMessage(message);
            processJsonResponse(message);
            logger.logHandleMessageOk();
        } catch (FmtaqException e) {
            logger.logFmtaqException(e, message);
        } catch (Exception e) {
            logger.logException(e, message);
        }
    }

    private void processJsonResponse(Message message) {
        assert message != null : "amqp message cannot be null";
        assert message.getBody() != null : "amqp message body cannot be null";

        String messageBody = new String(message.getBody(), CHARSET_UTF_8);
        CommandResponseDTO commandResponseDTO = commandResponseJsonConverter.toDTO(messageBody);
        commandService.handleResponse(commandResponseDTO);
    }
}
