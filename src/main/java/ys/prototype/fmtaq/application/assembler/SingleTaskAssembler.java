package ys.prototype.fmtaq.application.assembler;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import ys.prototype.fmtaq.application.dto.CommandDTO;
import ys.prototype.fmtaq.application.dto.TaskDTO;
import ys.prototype.fmtaq.domain.CommandStatus;
import ys.prototype.fmtaq.domain.TaskStatus;
import ys.prototype.fmtaq.domain.singletask.SingleCommand;
import ys.prototype.fmtaq.domain.singletask.SingleTask;
import ys.prototype.fmtaq.domain.task.Command;
import ys.prototype.fmtaq.domain.task.CommandSender;
import ys.prototype.fmtaq.domain.task.Task;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class SingleTaskAssembler {

    private final CommandSender sendService;

    public SingleTaskAssembler(@Qualifier(value = "commandAmqpSender") CommandSender sendService) {
        this.sendService = sendService;
    }

    Task fromDTO(TaskDTO taskDTO) {
        SingleTask singleTask = new SingleTask(UUID.randomUUID(), TaskStatus.REGISTERED, sendService);
        singleTask.setCommandSet(createSingleCommandSet(singleTask, taskDTO.getCommandList()));

        return singleTask;
    }

    private Set<Command> createSingleCommandSet(SingleTask singleTask, List<CommandDTO> commandDTOList) {
        return commandDTOList.stream().limit(1).map(commandDTO -> createSingleCommand(singleTask, commandDTO))
                .collect(Collectors.toSet());
    }

    private Command createSingleCommand(SingleTask singleTask, CommandDTO commandDTO) {
        return new SingleCommand(UUID.randomUUID(), commandDTO.getAddress(), commandDTO.getBody(),
                CommandStatus.REGISTERED, singleTask, sendService);
    }
}
