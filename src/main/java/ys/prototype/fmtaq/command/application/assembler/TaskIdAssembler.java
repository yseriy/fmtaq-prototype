package ys.prototype.fmtaq.command.application.assembler;

import org.springframework.stereotype.Component;
import ys.prototype.fmtaq.command.application.dto.TaskIdDTO;

import java.util.UUID;

@Component
public class TaskIdAssembler {

    public TaskIdDTO toDTO(UUID id) {
        return new TaskIdDTO(id.toString());
    }
}
