package ys.prototype.fmtaq.command.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ys.prototype.fmtaq.command.domain.task.Task;
import ys.prototype.fmtaq.command.domain.task.TaskFactory;
import ys.prototype.fmtaq.command.domain.task.TaskRepository;
import ys.prototype.fmtaq.command.dto.TaskDTO;
import ys.prototype.fmtaq.command.infrastructure.messaging.amqp.CommandSender;

import java.util.UUID;

@Service
@Transactional
public class TaskService {

    private final TaskFactory taskFactory;
    private final TaskRepository taskRepository;
    private final CommandSender commandSender;

    public TaskService(TaskFactory taskFactory, TaskRepository taskRepository, CommandSender commandSender) {
        this.taskFactory = taskFactory;
        this.taskRepository = taskRepository;
        this.commandSender = commandSender;
    }

    public UUID startTask(TaskDTO taskDTO) {
        Task task = taskFactory.createTask(taskDTO);
        taskRepository.save(task);
        commandSender.bulkSend(task.getStartCommandSet());

        return task.getId();
    }
}
