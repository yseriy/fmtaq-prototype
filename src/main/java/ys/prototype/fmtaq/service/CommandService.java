package ys.prototype.fmtaq.service;

import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Queue;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ys.prototype.fmtaq.domain.Command;

import java.util.Set;

@Service
@Transactional
public class CommandService {

    private final AmqpAdmin amqpAdmin;
    private final AmqpTemplate amqpTemplate;

    public CommandService(AmqpAdmin amqpAdmin, AmqpTemplate amqpTemplate) {
        this.amqpAdmin = amqpAdmin;
        this.amqpTemplate = amqpTemplate;
    }

    private void sendCommand(Command command) {
        amqpAdmin.declareQueue(new Queue(command.getAddress()));
        amqpTemplate.convertAndSend(command.getAddress(), command.getBody());
    }

    void bulkSendCommand(Set<Command> commands) {
        commands.forEach(this::sendCommand);
    }
}
