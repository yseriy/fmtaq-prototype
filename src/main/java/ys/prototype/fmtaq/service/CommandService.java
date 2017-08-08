package ys.prototype.fmtaq.service;

import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Queue;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ys.prototype.fmtaq.domain.Command;
import ys.prototype.fmtaq.domain.CommandConverter;
import ys.prototype.fmtaq.domain.CommandResultConverter;
import ys.prototype.fmtaq.domain.CommandStatus;
import ys.prototype.fmtaq.repository.CommandRepository;

import java.util.List;

@Service
@Transactional
public class CommandService {

    private static final Pageable LIMIT_1 = new PageRequest(0, 1);

    private final AmqpAdmin amqpAdmin;
    private final AmqpTemplate amqpTemplate;
    private final CommandRepository commandRepository;

    public CommandService(AmqpAdmin amqpAdmin, AmqpTemplate amqpTemplate, CommandRepository commandRepository) {
        this.amqpAdmin = amqpAdmin;
        this.amqpTemplate = amqpTemplate;
        this.commandRepository = commandRepository;
    }

    public Command setStatusAndGetNextCommand(CommandResultConverter converter) {
        Command command = commandRepository.findOne(converter.getCommandId());

        if (command == null) {
            throw new RuntimeException("command not found");
        }

        if (converter.isStatusOk()) {
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
        List<Command> nextCommandList = commandRepository.getNextCommand(command.getTask(),
                CommandStatus.REGISTERED, LIMIT_1);

        return nextCommandList.get(0);
    }

    private void sendCommand(Command command) {
        amqpAdmin.declareQueue(new Queue(command.getAddress()));
        amqpTemplate.convertAndSend(command.getAddress(), command.getBody());
    }
}
