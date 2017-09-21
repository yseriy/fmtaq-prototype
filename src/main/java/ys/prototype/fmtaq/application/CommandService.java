package ys.prototype.fmtaq.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ys.prototype.fmtaq.application.assembler.CommandAssembler;
import ys.prototype.fmtaq.application.dto.CommandResponseDTO;
import ys.prototype.fmtaq.domain.task.Command;

@Service
@Transactional
public class CommandService {

    private final CommandAssembler commandAssembler;

    public CommandService(CommandAssembler commandAssembler) {
        this.commandAssembler = commandAssembler;
    }

    public void handleResponse(CommandResponseDTO commandResponseDTO) {
        Command command = commandAssembler.getById(commandResponseDTO.getCommandId());
        command.handleResponse(commandResponseDTO.getResponseStatus());
    }
}
