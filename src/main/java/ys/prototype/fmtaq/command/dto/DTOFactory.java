package ys.prototype.fmtaq.command.dto;

import org.springframework.stereotype.Component;
import ys.prototype.fmtaq.command.domain.ResponseStatus;
import ys.prototype.fmtaq.command.domain.TaskType;

import java.util.List;
import java.util.UUID;

@Component
public class DTOFactory {

    public TaskDTO createTaskDTO(TaskType type, List<CommandDTO> commandDTOList) {
        return new TaskDTO(type, commandDTOList);
    }

    public CommandDTO createCommandDTO(String address, String body) {
        return new CommandDTO(address, body);
    }

    public ResponseDTO createResponseDTO(UUID commandId, ResponseStatus responseStatus, String body) {
        return new ResponseDTO(commandId, responseStatus, body);
    }
}
