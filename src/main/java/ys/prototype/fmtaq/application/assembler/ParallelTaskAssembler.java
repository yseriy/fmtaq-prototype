package ys.prototype.fmtaq.application.assembler;

import org.springframework.stereotype.Component;
import ys.prototype.fmtaq.application.dto.CommandDTO;
import ys.prototype.fmtaq.domain.CommandStatus;
import ys.prototype.fmtaq.domain.TaskStatus;
import ys.prototype.fmtaq.domain.command.Command;
import ys.prototype.fmtaq.domain.command.Task;
import ys.prototype.fmtaq.domain.parallel.command.ParallelCommand;
import ys.prototype.fmtaq.domain.parallel.command.ParallelTask;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class ParallelTaskAssembler {

    Task fromDTO(List<CommandDTO> commandDTOList) {
        ParallelTask parallelTask = new ParallelTask(UUID.randomUUID(), TaskStatus.REGISTERED, commandDTOList.size());
        Set<Command> commandSet = createGroupCommandSet(parallelTask, commandDTOList);
        parallelTask.setCommandSet(commandSet);

        return parallelTask;
    }

    private Set<Command> createGroupCommandSet(ParallelTask parallelTask, List<CommandDTO> commandDTOList) {
        return commandDTOList.stream().map(commandDTO -> createGroupCommand(commandDTO, parallelTask))
                .collect(Collectors.toSet());
    }

    private Command createGroupCommand(CommandDTO commandDTO, ParallelTask parallelTask) {
        return new ParallelCommand(UUID.randomUUID(), commandDTO.getAddress(), commandDTO.getBody(),
                CommandStatus.REGISTERED, parallelTask);
    }
}
