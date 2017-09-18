package ys.prototype.fmtaq.application.assembler;

import org.springframework.stereotype.Component;
import ys.prototype.fmtaq.domain.task.CommandSender;
import ys.prototype.fmtaq.domain.task.Command;
import ys.prototype.fmtaq.domain.task.CommandRepository;

import java.util.UUID;

@Component
public class CommandAssembler {

    private final CommandSender sendService;
    private final CommandRepository commandRepository;

    public CommandAssembler(CommandSender sendService, CommandRepository commandRepository) {
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
