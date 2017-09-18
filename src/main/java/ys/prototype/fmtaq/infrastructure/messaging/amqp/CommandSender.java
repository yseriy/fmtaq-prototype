package ys.prototype.fmtaq.infrastructure.messaging.amqp;

import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Queue;
import org.springframework.stereotype.Component;
import ys.prototype.fmtaq.domain.task.Command;

import java.util.Set;

@Component
public class CommandSender {

    private final AmqpAdmin amqpAdmin;
    private final AmqpTemplate amqpTemplate;

    public CommandSender(AmqpAdmin amqpAdmin, AmqpTemplate amqpTemplate) {
        this.amqpAdmin = amqpAdmin;
        this.amqpTemplate = amqpTemplate;
    }

    public void send(Command command) {
        amqpAdmin.declareQueue(new Queue(command.getAddress()));
        amqpTemplate.convertAndSend(command.getAddress(), command.getBody());
    }

    public void bulkSend(Set<Command> commands) {
        commands.forEach(this::send);
    }
}
