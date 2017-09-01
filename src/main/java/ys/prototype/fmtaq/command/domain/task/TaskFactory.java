package ys.prototype.fmtaq.command.domain.task;

import org.springframework.stereotype.Component;
import ys.prototype.fmtaq.command.domain.ModelFactory;
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
        Set<LinkedCommand> linkedCommands = new HashSet<>();
        ListIterator<CommandDTO> iterator = commandDTOList.listIterator(commandDTOList.size());

        UUID currentCommandId;
        UUID nextCommandId = null;

        while (iterator.hasPrevious()) {
            CommandDTO commandDTO = iterator.previous();
            currentCommandId = UUID.randomUUID();
            linkedCommands.add(linkedCommandFromDTO(currentCommandId, nextCommandId, commandDTO));
            nextCommandId = currentCommandId;
        }

        taskSequence.loadCommands(linkedCommands);
        taskSequence.setFirstCommandId(nextCommandId);

        return taskSequence;
    }

    private LinkedCommand linkedCommandFromDTO(UUID currentCommandId, UUID nextCommandId, CommandDTO commandDTO) {
        return modelFactory.createLinkedCommand(currentCommandId, nextCommandId, commandDTO.getAddress(), commandDTO.getBody());
    }

    private TaskGroup groupFromDTO(List<CommandDTO> commandDTOList) {
        TaskGroup taskGroup = modelFactory.createGroup(UUID.randomUUID());
        taskGroup.setCommandCounter(commandDTOList.size());
        taskGroup.loadCommands(commandDTOList.stream().map(this::groupedCommandFromDTO).collect(Collectors.toSet()));

        return taskGroup;
    }

    private GroupedCommand groupedCommandFromDTO(CommandDTO commandDTO) {
        return modelFactory.createGroupedCommand(UUID.randomUUID(), commandDTO.getAddress(), commandDTO.getBody());
    }
}
