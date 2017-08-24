package ys.prototype.fmtaq.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ys.prototype.fmtaq.domain.*;
import ys.prototype.fmtaq.domain.dto.CommandDTO;
import ys.prototype.fmtaq.domain.dto.TaskDTO;
import ys.prototype.fmtaq.repository.GroupCommandRepository;
import ys.prototype.fmtaq.repository.LinkedCommandRepository;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Transactional
public class TaskService {

    private final LinkedCommandRepository linkedCommandRepository;
    private final GroupCommandRepository groupCommandRepository;
    private final CommandService commandService;

    public TaskService(LinkedCommandRepository linkedCommandRepository, GroupCommandRepository groupCommandRepository,
                       CommandService commandService) {
        this.linkedCommandRepository = linkedCommandRepository;
        this.groupCommandRepository = groupCommandRepository;
        this.commandService = commandService;
    }

    public UUID registerTaskAndSendFirstCommand(TaskDTO taskDTO) {
        List<CommandDTO> commandDTOList = taskDTO.getCommandDTOList();
        Task task;

        switch (taskDTO.getType()) {
            case SEQUENCE:
                task = saveSequenceFromDTO(commandDTOList);
                break;
            case GROUP:
                task = saveGroupFromDTO(commandDTOList);
                break;
            default:
                throw new RuntimeException("cannot identify task type: " + taskDTO.getType());
        }

        commandService.bulkSendCommand(task.getCommandsForStart());

        return task.getId();
    }

    private Task saveGroupFromDTO(List<CommandDTO> commandDTOList) {
        final Group task = new Group(UUID.randomUUID(), commandDTOList.size());
        Function<CommandDTO, GroupCommand> mapper = dto -> new GroupCommand(UUID.randomUUID(), dto.getAddress(),
                dto.getBody(), task);
        groupCommandRepository.save(commandDTOList.stream().map(mapper).collect(Collectors.toSet()));

        return task;
    }

    private Task saveSequenceFromDTO(List<CommandDTO> commandDTOList) {
        Sequence task = new Sequence(UUID.randomUUID());
        Set<LinkedCommand> commands = loadCommandSetFromDTO(commandDTOList, task);
        linkedCommandRepository.save(commands);

        return task;
    }

    private Set<LinkedCommand> loadCommandSetFromDTO(List<CommandDTO> commandDTOList, Sequence task) {
        Set<LinkedCommand> commands = new HashSet<>();
        ListIterator<CommandDTO> iterator = commandDTOList.listIterator(commandDTOList.size());

        UUID currentCommandId;
        UUID nextCommandId = null;

        while (iterator.hasPrevious()) {
            CommandDTO commandDTO = iterator.previous();
            currentCommandId = UUID.randomUUID();
            commands.add(new LinkedCommand(currentCommandId, nextCommandId, commandDTO.getAddress(),
                    commandDTO.getBody(), task));
            nextCommandId = currentCommandId;
        }

        return commands;
    }
}
