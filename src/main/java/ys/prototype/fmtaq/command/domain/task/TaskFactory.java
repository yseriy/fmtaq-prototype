package ys.prototype.fmtaq.command.domain.task;

import org.springframework.stereotype.Component;
import ys.prototype.fmtaq.command.domain.ModelFactory;
import ys.prototype.fmtaq.command.domain.task.group.CommandGroup;
import ys.prototype.fmtaq.command.domain.task.group.TaskGroup;
import ys.prototype.fmtaq.command.domain.task.sequence.CommandSequence;
import ys.prototype.fmtaq.command.domain.task.sequence.TaskSequence;
import ys.prototype.fmtaq.command.dto.CommandDTO;
import ys.prototype.fmtaq.command.dto.TaskDTO;

import java.util.*;
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
                return sequenceFromDTO(taskDTO.getCommandDTOList());
            case GROUP:
                return groupFromDTO(taskDTO.getCommandDTOList());
            default:
                throw new RuntimeException("unknown task type: " + taskDTO.getType());
        }
    }

    private TaskSequence sequenceFromDTO(List<CommandDTO> commandDTOList) {
        TaskSequence taskSequence = modelFactory.createSequence(UUID.randomUUID());
        Set<CommandSequence> commandSequences = new HashSet<>();
        ListIterator<CommandDTO> iterator = commandDTOList.listIterator(commandDTOList.size());

        UUID currentCommandId;
        UUID nextCommandId = null;

        while (iterator.hasPrevious()) {
            CommandDTO commandDTO = iterator.previous();
            currentCommandId = UUID.randomUUID();
            commandSequences.add(commandSequenceFromDTO(currentCommandId, nextCommandId, commandDTO));
            nextCommandId = currentCommandId;
        }

        taskSequence.loadCommands(commandSequences);
        taskSequence.setFirstCommandId(nextCommandId);

        return taskSequence;
    }

    private CommandSequence commandSequenceFromDTO(UUID currentCommandId, UUID nextCommandId, CommandDTO commandDTO) {
        return modelFactory.createCommandSequence(currentCommandId, nextCommandId, commandDTO.getAddress(), commandDTO.getBody());
    }

    private TaskGroup groupFromDTO(List<CommandDTO> commandDTOList) {
        TaskGroup taskGroup = modelFactory.createGroup(UUID.randomUUID());
        taskGroup.setCommandCounter(commandDTOList.size());
        taskGroup.loadCommands(commandDTOList.stream().map(this::commandGroupFromDTO).collect(Collectors.toSet()));

        return taskGroup;
    }

    private CommandGroup commandGroupFromDTO(CommandDTO commandDTO) {
        return modelFactory.createCommandGroup(UUID.randomUUID(), commandDTO.getAddress(), commandDTO.getBody());
    }
}
