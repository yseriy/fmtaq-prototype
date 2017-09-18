package ys.prototype.fmtaq.application.assembler;

import org.springframework.stereotype.Component;
import ys.prototype.fmtaq.domain.CommandSendService;
import ys.prototype.fmtaq.domain.task.Command;
import ys.prototype.fmtaq.domain.task.CommandRepository;

import java.util.UUID;

@Component
public class CommandAssembler {

    private final CommandSendService sendService;
    private final CommandRepository commandRepository;

    public CommandAssembler(CommandSendService sendService, CommandRepository commandRepository) {
        this.sendService = sendService;
        this.commandRepository = commandRepository;
    }

    public Command getById(UUID id) {
        Command command = commandRepository.findOne(id);

        if (command == null) {
            throw new RuntimeException("cannot find command by id: " + id);
        }

        command.setSendService(sendService);
        command.getTask().setSendService(sendService);

        return command;
    }
}
