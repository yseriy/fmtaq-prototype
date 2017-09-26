package ys.prototype.fmtaq.application.assembler;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import ys.prototype.fmtaq.application.dto.CommandDTO;
import ys.prototype.fmtaq.application.dto.TaskDTO;
import ys.prototype.fmtaq.domain.CommandStatus;
import ys.prototype.fmtaq.domain.TaskStatus;
import ys.prototype.fmtaq.domain.singletask.SingleCommand;
import ys.prototype.fmtaq.domain.singletask.SingleTask;
import ys.prototype.fmtaq.domain.task.CommandSender;
import ys.prototype.fmtaq.domain.task.Task;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class SingleTaskAssembler {

    private final static Integer FIRST_ELEMENT = 1;
    private final CommandSender sendService;

    public SingleTaskAssembler(@Qualifier(value = "commandAmqpSender") CommandSender sendService) {
        this.sendService = sendService;
    }

    Task fromDTO(TaskDTO taskDTO) {
        SingleTask singleTask = createSingleTask();
        Set<SingleCommand> singleCommandSet = createSingleCommandSet(singleTask, taskDTO.getCommandList());
        singleTask.setCommandSet(new HashSet<>(singleCommandSet));

        return singleTask;
    }

    private SingleTask createSingleTask() {
        return new SingleTask(UUID.randomUUID(), TaskStatus.REGISTERED, sendService);
    }

    private Set<SingleCommand> createSingleCommandSet(SingleTask singleTask, List<CommandDTO> commandDTOList) {
        return commandDTOList.stream().limit(FIRST_ELEMENT)
                .map(commandDTO -> createSingleCommand(singleTask, commandDTO)).collect(Collectors.toSet());
    }

    private SingleCommand createSingleCommand(SingleTask singleTask, CommandDTO commandDTO) {
        return new SingleCommand(UUID.randomUUID(), commandDTO.getAddress(), commandDTO.getBody(),
                CommandStatus.REGISTERED, singleTask, sendService);
    }
}
