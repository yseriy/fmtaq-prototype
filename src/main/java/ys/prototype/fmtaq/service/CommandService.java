package ys.prototype.fmtaq.service;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ys.prototype.fmtaq.domain.Command;
import ys.prototype.fmtaq.domain.CommandResult;
import ys.prototype.fmtaq.domain.CommandStatus;
import ys.prototype.fmtaq.repository.CommandRepository;

import java.util.List;

@Service
@Transactional
public class CommandService {

    private final CommandRepository commandRepository;

    public CommandService(CommandRepository commandRepository) {
        this.commandRepository = commandRepository;
    }

    public Command setStatusAndGetNextCommand(CommandResult commandResult) {
        Command command = commandRepository.findOne(commandResult.getCommandId());

        if (command == null) {
            throw new RuntimeException("command not found");
        }

        if (commandResult.isStatusOk()) {
            return setStatusOkAndGetNextCommand(command);
        } else
            return setStatusErrorAndGetNextCommand(command);
    }

    private Command setStatusOkAndGetNextCommand(Command command) {
        command.setStatusOk();
        Command nextCommand = getNextCommand(command);

        if (nextCommand == null) {
            command.getTask().setStatusOk();
        }

        return nextCommand;
    }

    private Command setStatusErrorAndGetNextCommand(Command command) {
        command.setStatusError();
        command.getTask().setStatusError();

        return command.getTask().isErrorFatal() ? null : getNextCommand(command);
    }

    private Command getNextCommand(Command command) {
        Pageable limit1 = new PageRequest(0, 1);
        List<Command> nextCommandList = commandRepository.getNextCommand(command.getTask(), CommandStatus.REGISTERED, limit1);

        return nextCommandList.get(0);
    }
}
