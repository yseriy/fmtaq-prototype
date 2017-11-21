package ys.prototype.fmtaq.command.infrastructure.messaging.amqp;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CommandResponseQueueConfig {

    private final AmqpTransportQueueProperties amqpTransportQueueProperties;

    public CommandResponseQueueConfig(AmqpTransportQueueProperties amqpTransportQueueProperties) {
        this.amqpTransportQueueProperties = amqpTransportQueueProperties;
    }

    @Bean
    public Queue responseQueue() {
        String name = amqpTransportQueueProperties.getResponse().getName();
        Boolean durable = amqpTransportQueueProperties.getResponse().getDurable();
        Boolean exclusive = amqpTransportQueueProperties.getResponse().getExclusive();
        Boolean autoDelete = amqpTransportQueueProperties.getResponse().getAutoDelete();

        return new Queue(name, durable, exclusive, autoDelete);
    }
}
