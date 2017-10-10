package ys.prototype.fmtaq.application.assembler;

import org.springframework.stereotype.Component;
import ys.prototype.fmtaq.application.dto.CommandDTO;
import ys.prototype.fmtaq.application.dto.TaskDTO;
import ys.prototype.fmtaq.domain.TaskBuilderSelector;
import ys.prototype.fmtaq.domain.task.Task;
import ys.prototype.fmtaq.domain.task.TaskBuilder;

import java.util.List;

@Component
public class TaskAssembler {

    private final TaskBuilderSelector taskBuilderSelector;

    public TaskAssembler(TaskBuilderSelector taskBuilderSelector) {
        this.taskBuilderSelector = taskBuilderSelector;
    }

    public Task fromDTO(TaskDTO taskDTO) {
        TaskBuilder taskBuilder = taskBuilderSelector.getTaskBuilderByTaskType(taskDTO.getType());
        List<CommandDTO> commandDTOList = taskDTO.getCommandList();
        commandDTOList.forEach(commandDTO -> taskBuilder.addCommand(commandDTO.getAddress(), commandDTO.getBody()));

        return taskBuilder.build();
    }
}
