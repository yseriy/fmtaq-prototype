package ys.prototype.fmtaq.infrastructure.messaging.amqp;

import org.springframework.amqp.core.Message;
import org.springframework.stereotype.Component;

@Component
public interface CommandResponseHandler {

    void handle(Message message);
}
