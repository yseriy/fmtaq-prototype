package ys.prototype.fmtaq.application.assembler;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import ys.prototype.fmtaq.application.dto.CommandDTO;
import ys.prototype.fmtaq.application.dto.TaskDTO;
import ys.prototype.fmtaq.domain.CommandStatus;
import ys.prototype.fmtaq.domain.TaskStatus;
import ys.prototype.fmtaq.domain.parallel.task.ParallelCommand;
import ys.prototype.fmtaq.domain.parallel.task.ParallelTask;
import ys.prototype.fmtaq.domain.task.Command;
import ys.prototype.fmtaq.domain.task.CommandSender;
import ys.prototype.fmtaq.domain.task.Task;

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
        ParallelTask parallelTask = new ParallelTask(UUID.randomUUID(), TaskStatus.REGISTERED,
                taskDTO.getCommandList().size(), sendService);
        parallelTask.setCommandSet(createGroupCommandSet(parallelTask, taskDTO.getCommandList()));

        return parallelTask;
    }

    private Set<Command> createGroupCommandSet(ParallelTask parallelTask, List<CommandDTO> commandDTOList) {
        return commandDTOList.stream().map(commandDTO -> createGroupCommand(commandDTO, parallelTask))
                .collect(Collectors.toSet());
    }

    private Command createGroupCommand(CommandDTO commandDTO, ParallelTask parallelTask) {
        return new ParallelCommand(UUID.randomUUID(), commandDTO.getAddress(), commandDTO.getBody(),
                CommandStatus.REGISTERED, parallelTask, sendService);
    }
}
