package ys.prototype.fmtaq.command.dto;

import lombok.Data;
import ys.prototype.fmtaq.command.domain.TaskType;

import java.util.List;

@Data
public class TaskDTO {
    private TaskType type;
    private List<CommandDTO> commandDTOList;
}
