package ys.prototype.fmtaq.command.domain.task;

import org.springframework.stereotype.Component;
import ys.prototype.fmtaq.command.domain.ModelFactory;
import ys.prototype.fmtaq.command.domain.task.impl.ParallelCommand;
import ys.prototype.fmtaq.command.domain.task.impl.ParallelTask;
import ys.prototype.fmtaq.command.domain.task.impl.SequenceCommand;
import ys.prototype.fmtaq.command.domain.task.impl.SequenceTask;
import ys.prototype.fmtaq.command.dto.CommandDTO;
import ys.prototype.fmtaq.command.dto.TaskDTO;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class TaskFactory {

    private final ModelFactory modelFactory;

    public TaskFactory(ModelFactory modelFactory) {
        this.modelFactory = modelFactory;
    }

    public Task createTask(TaskDTO taskDTO) {
        switch (taskDTO.getType()) {
            case SEQUENCE:
                return sequenceTaskFromDTO(taskDTO.getCommandDTOList());
            case GROUP:
                return parallelTaskFromDTO(taskDTO.getCommandDTOList());
            default:
                throw new RuntimeException("unknown task type: " + taskDTO.getType());
        }
    }

    private SequenceTask sequenceTaskFromDTO(List<CommandDTO> commandDTOList) {
        SequenceTask sequenceTask = modelFactory.createSequenceTask(UUID.randomUUID());
        Set<SequenceCommand> commandSequences = new HashSet<>();
        ListIterator<CommandDTO> iterator = commandDTOList.listIterator(commandDTOList.size());

        UUID currentCommandId;
        UUID nextCommandId = null;

        while (iterator.hasPrevious()) {
            CommandDTO commandDTO = iterator.previous();
            currentCommandId = UUID.randomUUID();
            commandSequences.add(modelFactory.createSequenceCommand(currentCommandId, nextCommandId,
                    commandDTO.getAddress(), commandDTO.getBody(), sequenceTask));
            nextCommandId = currentCommandId;
        }

        sequenceTask.setSequenceCommands(commandSequences);
        sequenceTask.setFirstCommandId(nextCommandId);

        return sequenceTask;
    }

    private ParallelTask parallelTaskFromDTO(List<CommandDTO> commandDTOList) {
        ParallelTask parallelTask = modelFactory.createParallelTask(UUID.randomUUID());
        parallelTask.setCommandCounter(commandDTOList.size());
        Function<CommandDTO, ParallelCommand> mapper = commandDTO -> modelFactory.createParallelCommand(
                UUID.randomUUID(), commandDTO.getAddress(), commandDTO.getBody(), parallelTask);
        parallelTask.setParallelCommands(commandDTOList.stream().map(mapper).collect(Collectors.toSet()));

        return parallelTask;
    }
}
