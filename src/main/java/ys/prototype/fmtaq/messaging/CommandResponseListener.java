package ys.prototype.fmtaq.messaging;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import ys.prototype.fmtaq.service.CommandService;

import java.io.IOException;

@Component
public class CommandResponseListener {

    private final CommandService commandService;
    private final MessageToDTOConverter toDTOConverter;

    public CommandResponseListener(CommandService commandService, MessageToDTOConverter toDTOConverter) {
        this.commandService = commandService;
        this.toDTOConverter = toDTOConverter;
    }

    @RabbitListener(bindings = @QueueBinding(value = @Queue("response"), exchange = @Exchange(""), key = "response"))
    public void processResponse(Message message) throws IOException {
    }
}
