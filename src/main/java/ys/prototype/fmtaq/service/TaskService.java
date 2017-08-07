package ys.prototype.fmtaq.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ys.prototype.fmtaq.domain.*;
import ys.prototype.fmtaq.repository.CommandRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
@Transactional
public class TaskService {

    private final CommandRepository commandRepository;

    public TaskService(CommandRepository commandRepository) {
        this.commandRepository = commandRepository;
    }

    public UUID registerTask(TaskData taskData) {
        Task task = new Task(TaskStatus.REGISTERED, taskData.getType());
        commandRepository.save(copyCommandSetAndSetTask(taskData.getCommands(), task));

        return task.getId();
    }

    private Set<Command> copyCommandSetAndSetTask(List<CommandData> commandDataList, Task task) {
        Set<Command> commandSetTo = new HashSet<>();

        for (Integer i = 0; i < commandDataList.size(); i++) {
            commandSetTo.add(copyCommandAndSetTask(commandDataList.get(i), i, task));
        }

        return commandSetTo;
    }

    private Command copyCommandAndSetTask(CommandData commandData, Integer step, Task task) {
        return new Command(commandData.getAddress(), commandData.getBody(), CommandStatus.REGISTERED, step, task);
    }
}
