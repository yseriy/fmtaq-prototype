package ys.prototype.fmtaq.command.application.assembler;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import ys.prototype.fmtaq.command.application.ApplicationErrorList;
import ys.prototype.fmtaq.command.domain.FmtaqException;
import ys.prototype.fmtaq.command.domain.task.Command;
import ys.prototype.fmtaq.command.domain.task.CommandRepository;
import ys.prototype.fmtaq.command.domain.task.CommandSender;

import java.util.Optional;
import java.util.UUID;

@Component
public class CommandAssembler {

    private final CommandSender sendService;
    private final CommandRepository commandRepository;

    public CommandAssembler(@Qualifier(value = "amqpTransportCommandSender") CommandSender sendService,
                            CommandRepository commandRepository) {
        this.sendService = sendService;
        this.commandRepository = commandRepository;
    }

    public Command getById(UUID id) {
        Optional<Command> commandOptional = Optional.ofNullable(commandRepository.findOne(id));
        Command command = commandOptional.orElseThrow(() -> commandNotFound(id));
        command.setCommandSender(sendService);
        command.getTask().setCommandSender(sendService);

        return command;
    }

    private FmtaqException commandNotFound(UUID id) {
        return new FmtaqException(ApplicationErrorList.COMMAND_NOT_FOUND).set("command id", id.toString());
    }
}
