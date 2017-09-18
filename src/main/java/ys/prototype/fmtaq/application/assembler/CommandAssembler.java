package ys.prototype.fmtaq.application.assembler;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import ys.prototype.fmtaq.domain.task.Command;
import ys.prototype.fmtaq.domain.task.CommandRepository;
import ys.prototype.fmtaq.domain.task.CommandSender;

import java.util.UUID;

@Component
public class CommandAssembler {

    private final CommandSender sendService;
    private final CommandRepository commandRepository;

    public CommandAssembler(@Qualifier(value = "commandAmqpSender") CommandSender sendService,
                            CommandRepository commandRepository) {
        this.sendService = sendService;
        this.commandRepository = commandRepository;
    }

    public Command getById(UUID id) {
        Command command = commandRepository.findOne(id);

        if (command == null) {
            throw new RuntimeException("cannot find command by id: " + id);
        }

        command.setCommandSender(sendService);
        command.getTask().setCommandSender(sendService);

        return command;
    }
}
