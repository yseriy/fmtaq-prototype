package ys.prototype.fmtaq.infrastructure.messaging.amqp;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CommandResponseQueueConfig {

    private final TransportQueueProperties transportQueueProperties;

    public CommandResponseQueueConfig(TransportQueueProperties transportQueueProperties) {
        this.transportQueueProperties = transportQueueProperties;
    }

    @Bean
    public Queue responseQueue() {
        String name = transportQueueProperties.getResponse().getName();
        Boolean durable = transportQueueProperties.getResponse().getDurable();
        Boolean exclusive = transportQueueProperties.getResponse().getExclusive();
        Boolean autoDelete = transportQueueProperties.getResponse().getAutoDelete();

        return new Queue(name, durable, exclusive, autoDelete);
    }
}
