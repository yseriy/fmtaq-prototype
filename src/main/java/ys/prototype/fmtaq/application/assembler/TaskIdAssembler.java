package ys.prototype.fmtaq.application.assembler;

import org.springframework.stereotype.Component;
import ys.prototype.fmtaq.application.dto.TaskIdDTO;

import java.util.UUID;

@Component
public class TaskIdAssembler {

    public TaskIdDTO toDTO(UUID id) {
        return new TaskIdDTO(id.toString());
    }
}
