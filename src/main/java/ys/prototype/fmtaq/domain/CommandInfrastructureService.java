package ys.prototype.fmtaq.domain;

import org.springframework.stereotype.Service;
import ys.prototype.fmtaq.infrastructure.messaging.amqp.CommandSender;

import java.util.UUID;

@Service
public class CommandInfrastructureService {

    private final CommandRepository commandRepository;
    private final CommandSender commandSender;

    public CommandInfrastructureService(CommandRepository commandRepository, CommandSender commandSender) {
        this.commandRepository = commandRepository;
        this.commandSender = commandSender;
    }

    public Command findCommandById(UUID id) {
        return commandRepository.findOne(id);
    }

    public void sendCommand(Command command) {
        commandSender.send(command);
    }
}
