package ys.prototype.fmtaq.application.assembler;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import ys.prototype.fmtaq.domain.task.Command;
import ys.prototype.fmtaq.domain.task.CommandRepository;
import ys.prototype.fmtaq.domain.task.CommandSender;
import ys.prototype.fmtaq.exception.FmtaqErrorList;
import ys.prototype.fmtaq.exception.FmtaqException;

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
        return new FmtaqException(FmtaqErrorList.COMMAND_NOT_FOUND).set("command id", id.toString());
    }
}
