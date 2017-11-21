package ys.prototype.fmtaq.command.application.assembler;

import org.springframework.stereotype.Component;
import ys.prototype.fmtaq.command.application.dto.CommandDTO;
import ys.prototype.fmtaq.command.application.dto.TaskDTO;
import ys.prototype.fmtaq.command.domain.task.Task;
import ys.prototype.fmtaq.command.domain.task.TaskBuilder;

import java.util.List;

@Component
public class TaskAssembler {

    private final TaskBuilderSelector taskBuilderSelector;

    public TaskAssembler(TaskBuilderSelector taskBuilderSelector) {
        this.taskBuilderSelector = taskBuilderSelector;
    }

    public Task fromDTO(TaskDTO taskDTO) {
        TaskBuilder taskBuilder = taskBuilderSelector.getTaskBuilderByTaskType(taskDTO.getType())
                .setAccount(taskDTO.getAccount()).setServiceType(taskDTO.getServiceType());
        List<CommandDTO> commandDTOList = taskDTO.getCommandList();
        commandDTOList.forEach(commandDTO -> taskBuilder.addCommand(commandDTO.getAddress(), commandDTO.getBody()));

        return taskBuilder.build();
    }
}
