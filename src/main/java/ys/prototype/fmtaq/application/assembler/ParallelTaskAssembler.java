package ys.prototype.fmtaq.application.assembler;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import ys.prototype.fmtaq.application.dto.CommandDTO;
import ys.prototype.fmtaq.application.dto.TaskDTO;
import ys.prototype.fmtaq.domain.CommandStatus;
import ys.prototype.fmtaq.domain.TaskStatus;
import ys.prototype.fmtaq.domain.paralleltask.ParallelCommand;
import ys.prototype.fmtaq.domain.paralleltask.ParallelTask;
import ys.prototype.fmtaq.domain.task.CommandSender;
import ys.prototype.fmtaq.domain.task.Task;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class ParallelTaskAssembler {

    private final CommandSender sendService;

    public ParallelTaskAssembler(@Qualifier(value = "commandAmqpSender") CommandSender sendService) {
        this.sendService = sendService;
    }

    Task fromDTO(TaskDTO taskDTO) {
        Integer commandCounter = taskDTO.getCommandList().size();
        ParallelTask parallelTask = createParallelTask(commandCounter);
        Set<ParallelCommand> parallelCommandSet = createParallelCommandSet(parallelTask, taskDTO.getCommandList());
        parallelTask.setCommandSet(new HashSet<>(parallelCommandSet));

        return parallelTask;
    }

    private ParallelTask createParallelTask(Integer commandCounter) {
        return new ParallelTask(UUID.randomUUID(), TaskStatus.REGISTERED, commandCounter, sendService);
    }

    private Set<ParallelCommand> createParallelCommandSet(ParallelTask parallelTask, List<CommandDTO> commandDTOList) {
        return commandDTOList.stream().map(commandDTO -> createParallelCommand(parallelTask, commandDTO))
                .collect(Collectors.toSet());
    }

    private ParallelCommand createParallelCommand(ParallelTask parallelTask, CommandDTO commandDTO) {
        return new ParallelCommand(UUID.randomUUID(), commandDTO.getAddress(), commandDTO.getBody(),
                CommandStatus.REGISTERED, parallelTask, sendService);
    }
}
