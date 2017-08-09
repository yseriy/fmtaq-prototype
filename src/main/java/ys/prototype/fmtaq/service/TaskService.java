package ys.prototype.fmtaq.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ys.prototype.fmtaq.domain.Command;
import ys.prototype.fmtaq.domain.CommandStatus;
import ys.prototype.fmtaq.domain.Task;
import ys.prototype.fmtaq.domain.TaskStatus;
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
        Task task = new Task(TaskStatus.REGISTERED, taskDTO.getType());
        List<CommandDTO> commandDTOList = taskDTO.getCommandDTOList();
        Set<Command> commandSetTo = new HashSet<>();

        Command firstCommand = copyCommand(commandDTOList.get(STEP_0), STEP_0);

        for (Integer step = STEP_1; step < commandDTOList.size(); step++) {
            commandSetTo.add(copyCommand(commandDTOList.get(step), step));
        }

        commandSetTo.add(firstCommand);
        task.setCommands(commandSetTo);
        taskRepository.save(task);
        commandService.sendCommand(firstCommand);

        return task.getId();
    }

    private Command copyCommand(CommandDTO commandDTO, Integer step) {
        return new Command(commandDTO.getAddress(), commandDTO.getBody(), CommandStatus.REGISTERED, step);
    }
}
