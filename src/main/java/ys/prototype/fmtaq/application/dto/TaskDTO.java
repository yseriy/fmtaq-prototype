package ys.prototype.fmtaq.application.dto;

import lombok.Data;
import ys.prototype.fmtaq.domain.TaskType;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
public class TaskDTO {

    @NotNull(message = "TaskType cannot be null")
    private final TaskType type;

    @Valid
    @NotNull(message = "CommandList cannot be null")
    @Size(min = 1)
    private final List<CommandDTO> commandList;
}
