package ys.prototype.fmtaq.service;

import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ys.prototype.fmtaq.domain.Command;

import java.util.Set;

@Service
@Transactional
public class CommandService {

    private final AmqpAdmin amqpAdmin;
    private final AmqpTemplate amqpTemplate;
    private final FmtaqFactory fmtaqFactory;

    public CommandService(AmqpAdmin amqpAdmin, AmqpTemplate amqpTemplate, FmtaqFactory fmtaqFactory) {
        this.amqpAdmin = amqpAdmin;
        this.amqpTemplate = amqpTemplate;
        this.fmtaqFactory = fmtaqFactory;
    }

    private void sendCommand(Command command) {
        amqpAdmin.declareQueue(fmtaqFactory.getQueue(command.getAddress()));
        amqpTemplate.convertAndSend(command.getAddress(), command.getBody());
    }

    void bulkSendCommand(Set<Command> commands) {
        commands.forEach(this::sendCommand);
    }
}
