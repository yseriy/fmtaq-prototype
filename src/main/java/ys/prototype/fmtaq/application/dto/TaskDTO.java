package ys.prototype.fmtaq.application.dto;

import lombok.Data;
import ys.prototype.fmtaq.domain.TaskType;

import java.util.List;

@Data
public class TaskDTO {
    private TaskType type;
    private List<CommandDTO> commandDTOList;
}
