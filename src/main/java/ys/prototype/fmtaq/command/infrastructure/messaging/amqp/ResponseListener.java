package ys.prototype.fmtaq.command.infrastructure.messaging.amqp;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class ResponseListener {

    private final MessageToResponseDTOMapper messageToResponseDTOMapper;

    public ResponseListener(MessageToResponseDTOMapper messageToResponseDTOMapper) {
        this.messageToResponseDTOMapper = messageToResponseDTOMapper;
    }

    @RabbitListener(bindings = @QueueBinding(value = @Queue("response"), exchange = @Exchange(""), key = "response"))
    public void processResponse(Message message) throws IOException {
    }
}
