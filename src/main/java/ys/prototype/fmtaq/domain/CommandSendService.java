package ys.prototype.fmtaq.domain;

import org.springframework.stereotype.Service;
import ys.prototype.fmtaq.domain.task.Command;
import ys.prototype.fmtaq.infrastructure.messaging.amqp.CommandSender;

@Service
public class CommandSendService {

    private final CommandSender commandSender;

    public CommandSendService(CommandSender commandSender) {
        this.commandSender = commandSender;
    }

    public void sendCommand(Command command) {
        commandSender.send(command);
    }
}
