package ys.prototype.fmtaq.service;

import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Queue;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ys.prototype.fmtaq.domain.Command;
import ys.prototype.fmtaq.domain.CommandStatus;
import ys.prototype.fmtaq.domain.dto.CommandResponseDTO;
import ys.prototype.fmtaq.repository.CommandRepository;

@Service
@Transactional
public class CommandService {

    private final AmqpAdmin amqpAdmin;
    private final AmqpTemplate amqpTemplate;
    private final CommandRepository commandRepository;

    public CommandService(AmqpAdmin amqpAdmin, AmqpTemplate amqpTemplate, CommandRepository commandRepository) {
        this.amqpAdmin = amqpAdmin;
        this.amqpTemplate = amqpTemplate;
        this.commandRepository = commandRepository;
    }

    public void setStatusAndSendNextCommand(CommandResponseDTO commandResponseDTO) {
        Command command = commandRepository.findOne(commandResponseDTO.getCommandId());

        if (command == null) {
            throw new RuntimeException("command not found");
        }

        command.setStatusFromResponse(commandResponseDTO.getResponseStatus());

        if (command.hasNextCommand()) {
            Command nextCommand = commandRepository.getNextCommand(command.getTask(), CommandStatus.REGISTERED,
                    command.nextStep());

            if (nextCommand == null) {
                throw new RuntimeException("cannot found next command");
            }

            sendCommand(nextCommand);
        }
    }

    void sendCommand(Command command) {
        amqpAdmin.declareQueue(new Queue(command.getAddress()));
        amqpTemplate.convertAndSend(command.getAddress(), command.getBody());
    }
}
