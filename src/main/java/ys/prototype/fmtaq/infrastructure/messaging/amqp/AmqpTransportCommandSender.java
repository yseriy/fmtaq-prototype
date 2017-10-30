package ys.prototype.fmtaq.infrastructure.messaging.amqp;

import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Queue;
import org.springframework.stereotype.Component;
import ys.prototype.fmtaq.domain.FmtaqError;
import ys.prototype.fmtaq.domain.FmtaqException;
import ys.prototype.fmtaq.domain.task.Command;
import ys.prototype.fmtaq.domain.task.CommandSender;

@Component
public class AmqpTransportCommandSender implements CommandSender {

    private final AmqpAdmin amqpAdmin;
    private final AmqpTemplate amqpTemplate;
    private final AmqpTransportQueueProperties amqpTransportQueueProperties;

    public AmqpTransportCommandSender(AmqpAdmin amqpAdmin, AmqpTemplate amqpTemplate,
                                      AmqpTransportQueueProperties amqpTransportQueueProperties) {
        this.amqpAdmin = amqpAdmin;
        this.amqpTemplate = amqpTemplate;
        this.amqpTransportQueueProperties = amqpTransportQueueProperties;
    }

    public void send(Command command) {
        assert command != null : "command cannot be null";
        assert command.getAddress() != null : "command address cannot be null";
        assert command.getBody() != null : "command body cannot be null";

        Queue queue = createQueue(command.getAddress());
        declareQueue(queue);
        convertAndSend(command.getAddress(), command.getBody());
    }

    private Queue createQueue(String name) {
        Boolean durable = amqpTransportQueueProperties.getHost().getDurable();
        Boolean exclusive = amqpTransportQueueProperties.getHost().getExclusive();
        Boolean autoDelete = amqpTransportQueueProperties.getHost().getAutoDelete();

        return new Queue(name, durable, exclusive, autoDelete);
    }

    private void declareQueue(Queue queue) {
        try {
            amqpAdmin.declareQueue(queue);
        } catch (AmqpException e) {
            throw exception(AmqpTransportErrorList.CANNOT_DECLARE_QUEUE, e).set("queue", queue.toString());
        }
    }

    private void convertAndSend(String address, String body) {
        try {
            amqpTemplate.convertAndSend(address, body);
        } catch (AmqpException e) {
            throw exception(AmqpTransportErrorList.CANNOT_SEND_COMMAND, e).set("address", address).set("body", body);
        }
    }

    private FmtaqException exception(FmtaqError fmtaqError, Throwable cause) {
        return new FmtaqException(fmtaqError, cause);
    }
}
