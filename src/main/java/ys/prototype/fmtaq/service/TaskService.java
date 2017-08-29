package ys.prototype.fmtaq.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ys.prototype.fmtaq.domain.*;
import ys.prototype.fmtaq.domain.dto.CommandDTO;
import ys.prototype.fmtaq.domain.dto.TaskDTO;
import ys.prototype.fmtaq.repository.TaskRepository;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class TaskService {

    private final ModelFactory modelFactory;
    private final TaskRepository taskRepository;

    public TaskService(ModelFactory modelFactory, TaskRepository taskRepository) {
        this.modelFactory = modelFactory;
        this.taskRepository = taskRepository;
    }

    public void startTask(TaskDTO taskDTO) {
        Task task = fromDTO(taskDTO);
        taskRepository.save(task);
    }

    private Task fromDTO(TaskDTO taskDTO) {
        switch (taskDTO.getType()) {
            case SEQUENCE:
                return sequenceFromDTO(taskDTO.getCommandDTOList());
            case GROUP:
                return groupFromDTO(taskDTO.getCommandDTOList());
            default:
                throw new RuntimeException("unknown task type: " + taskDTO.getType());
        }
    }

    private Sequence sequenceFromDTO(List<CommandDTO> commandDTOList) {
        Sequence sequence = modelFactory.createSequence(UUID.randomUUID());
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

        sequence.loadCommands(linkedCommands);
        sequence.setFirstCommandId(nextCommandId);

        return sequence;
    }

    private LinkedCommand linkedCommandFromDTO(UUID currentCommandId, UUID nextCommandId, CommandDTO commandDTO) {
        return modelFactory.createLinkedCommand(currentCommandId, nextCommandId, commandDTO.getAddress(), commandDTO.getBody());
    }

    private Group groupFromDTO(List<CommandDTO> commandDTOList) {
        Group group = modelFactory.createGroup(UUID.randomUUID());
        group.setCommandCounter(commandDTOList.size());
        group.loadCommands(commandDTOList.stream().map(this::groupedCommandFromDTO).collect(Collectors.toSet()));

        return group;
    }

    private GroupedCommand groupedCommandFromDTO(CommandDTO commandDTO) {
        return modelFactory.createGroupedCommand(UUID.randomUUID(), commandDTO.getAddress(), commandDTO.getBody());
    }
}
