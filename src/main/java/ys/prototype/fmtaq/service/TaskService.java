package ys.prototype.fmtaq.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ys.prototype.fmtaq.domain.Command;
import ys.prototype.fmtaq.domain.Task;
import ys.prototype.fmtaq.domain.TaskStatus;
import ys.prototype.fmtaq.domain.TaskType;
import ys.prototype.fmtaq.repository.CommandRepository;
import ys.prototype.fmtaq.repository.TaskRepository;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Service
@Transactional
public class TaskService {

    private final TaskRepository taskRepository;
    private final CommandRepository commandRepository;

    public TaskService(TaskRepository taskRepository, CommandRepository commandRepository) {
        this.taskRepository = taskRepository;
        this.commandRepository = commandRepository;
    }

    public UUID registerTask(TaskType taskType, Set<Command> commandSet) {
        Task task = new Task(TaskStatus.REGISTERED, taskType);

        taskRepository.save(task);
        commandRepository.save(copyCommandSetAndSetTask(commandSet, task));

        return task.getId();
    }

    private Set<Command> copyCommandSetAndSetTask(Set<Command> commandSetFrom, Task task) {
        Set<Command> commandSetTo = new HashSet<>();

        for (Command commandFrom : commandSetFrom) {
            commandSetTo.add(copyCommandAndSetTask(commandFrom, task));
        }

        return commandSetTo;
    }

    private Command copyCommandAndSetTask(Command commandFrom, Task task) {
        return new Command(commandFrom.getAddress(), commandFrom.getBody(), commandFrom.getStatus(),
                commandFrom.getStep(), task);
    }
}
