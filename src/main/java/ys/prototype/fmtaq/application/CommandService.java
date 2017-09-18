package ys.prototype.fmtaq.application;

import org.springframework.stereotype.Service;
import ys.prototype.fmtaq.application.assembler.CommandAssembler;
import ys.prototype.fmtaq.application.dto.CommandResponseDTO;
import ys.prototype.fmtaq.domain.CommandResponseStatus;
import ys.prototype.fmtaq.domain.command.Command;

import java.util.UUID;

@Service
public class CommandService {

    private final CommandAssembler commandAssembler;

    public CommandService(CommandAssembler commandAssembler) {
        this.commandAssembler = commandAssembler;
    }

    public void handleResponse(CommandResponseDTO commandResponseDTO) {
        UUID commandId = commandResponseDTO.getCommandId();
        CommandResponseStatus commandResponseStatus = commandResponseDTO.getResponseStatus();
        Command command = commandAssembler.getById(commandId);
        command.handleResponse(commandResponseStatus);
    }
}
