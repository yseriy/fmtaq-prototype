package ys.prototype.fmtaq.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ys.prototype.fmtaq.domain.Command;
import ys.prototype.fmtaq.domain.Group;
import ys.prototype.fmtaq.domain.Sequence;
import ys.prototype.fmtaq.domain.Task;
import ys.prototype.fmtaq.domain.dto.CommandDTO;
import ys.prototype.fmtaq.domain.dto.TaskDTO;
import ys.prototype.fmtaq.repository.TaskRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
@Transactional
public class TaskService {

    private static final Integer STEP_0 = 0;
    private static final Integer STEP_1 = 1;
    private final TaskRepository taskRepository;
    private final CommandService commandService;

    public TaskService(TaskRepository taskRepository, CommandService commandService) {
        this.taskRepository = taskRepository;
        this.commandService = commandService;
    }

    public UUID registerTaskAndSendFirstCommand(TaskDTO taskDTO) {
        List<CommandDTO> commandDTOList = taskDTO.getCommandDTOList();
        Integer commandCounter = commandDTOList.size();
        Set<Command> commandSet = new HashSet<>();

        Task task;

        switch (taskDTO.getType()) {
            case SEQUENCE:
                task = new Sequence(commandCounter);
                break;
            case GROUP:
                task = new Group(commandCounter);
                break;
            default:
                throw new RuntimeException("cannot identify task type: " + taskDTO.getType());
        }

        Command firstCommand = copyCommand(commandDTOList.get(STEP_0), STEP_0, task);

        for (Integer step = STEP_1; step < commandCounter; step++) {
            commandSet.add(copyCommand(commandDTOList.get(step), step, task));
        }

        commandSet.add(firstCommand);
        task.setCommands(commandSet);
        taskRepository.save(task);
        commandService.sendCommand(firstCommand);

        return task.getId();
    }

    private Command copyCommand(CommandDTO commandDTO, Integer step, Task task) {
        return new Command(commandDTO.getAddress(), commandDTO.getBody(), step, task);
    }
}
