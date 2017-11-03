package ys.prototype.fmtaq.infrastructure.messaging.amqp.v1;

import org.springframework.amqp.core.Message;
import org.springframework.stereotype.Component;
import ys.prototype.fmtaq.application.CommandService;
import ys.prototype.fmtaq.application.dto.CommandResponseDTO;
import ys.prototype.fmtaq.infrastructure.messaging.amqp.CommandResponseHandler;
import ys.prototype.fmtaq.infrastructure.messaging.amqp.CommandResponseListener;

import java.nio.charset.Charset;

@Component
public class CommandResponseHandlerV1 implements CommandResponseHandler {

    private final static String HANDLER_VERSION = "v1";
    private final static Charset CHARSET_UTF_8 = Charset.forName("UTF-8");
    private final CommandResponseJsonConverter commandResponseJsonConverter;
    private final CommandService commandService;

    public CommandResponseHandlerV1(CommandResponseJsonConverter commandResponseJsonConverter,
                                    CommandService commandService, CommandResponseListener commandResponseListener) {
        this.commandResponseJsonConverter = commandResponseJsonConverter;
        this.commandService = commandService;
        commandResponseListener.addVersionHandler(HANDLER_VERSION, this);
    }

    @Override
    public void handle(Message message) {
        String messageBody = new String(message.getBody(), CHARSET_UTF_8);
        CommandResponseDTO commandResponseDTO = commandResponseJsonConverter.toDTO(messageBody);
        commandService.handleResponse(commandResponseDTO);
    }
}
