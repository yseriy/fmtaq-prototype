package ys.prototype.fmtaq.command.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ys.prototype.fmtaq.command.domain.task.Command;
import ys.prototype.fmtaq.command.domain.task.CommandRepository;
import ys.prototype.fmtaq.command.dto.ResponseDTO;
import ys.prototype.fmtaq.command.infrastructure.messaging.amqp.CommandSender;

@Service
@Transactional
public class CommandService {

    private final CommandSender commandSender;
    private final CommandRepository commandRepository;

    public CommandService(CommandSender commandSender, CommandRepository commandRepository) {
        this.commandSender = commandSender;
        this.commandRepository = commandRepository;
    }

    public void updateAndSend(ResponseDTO responseDTO) {
        Command command = commandRepository.findOne(responseDTO.getCommandId());

        if (command == null) {
            throw new RuntimeException("cannot find command by id: " + responseDTO.getCommandId());
        }
    }
}
