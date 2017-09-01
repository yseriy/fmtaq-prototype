package ys.prototype.fmtaq.command.dto;

import org.springframework.stereotype.Component;
import ys.prototype.fmtaq.command.domain.ResponseStatus;

import java.util.UUID;

@Component
public class DTOFactory {

    public TaskDTO createTaskDTO() {
        return new TaskDTO();
    }

    public CommandDTO createCommandDTO(String address, String body) {
        return new CommandDTO(address, body);
    }

    public ResponseDTO createResponseDTO(UUID commandId, ResponseStatus responseStatus, String body) {
        return new ResponseDTO(commandId, responseStatus, body);
    }
}
