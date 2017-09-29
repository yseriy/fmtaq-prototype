package ys.prototype.fmtaq.infrastructure.messaging.amqp;

import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Queue;
import org.springframework.stereotype.Component;
import ys.prototype.fmtaq.domain.task.Command;
import ys.prototype.fmtaq.domain.task.CommandSender;

@Component
public class CommandAmqpSender implements CommandSender {

    private final AmqpAdmin amqpAdmin;
    private final AmqpTemplate amqpTemplate;
    private final TransportQueueProperties transportQueueProperties;

    public CommandAmqpSender(AmqpAdmin amqpAdmin, AmqpTemplate amqpTemplate,
                             TransportQueueProperties transportQueueProperties) {
        this.amqpAdmin = amqpAdmin;
        this.amqpTemplate = amqpTemplate;
        this.transportQueueProperties = transportQueueProperties;
    }

    public void send(Command command) {
        Queue queue = createQueue(command.getAddress());
        amqpAdmin.declareQueue(queue);
        amqpTemplate.convertAndSend(command.getAddress(), command.getBody());
    }

    private Queue createQueue(String name) {
        Boolean durable = transportQueueProperties.getHost().getDurable();
        Boolean exclusive = transportQueueProperties.getHost().getExclusive();
        Boolean autoDelete = transportQueueProperties.getHost().getAutoDelete();

        return new Queue(name, durable, exclusive, autoDelete);
    }
}
