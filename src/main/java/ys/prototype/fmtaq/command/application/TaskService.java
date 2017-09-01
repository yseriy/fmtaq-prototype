package ys.prototype.fmtaq.command.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ys.prototype.fmtaq.command.domain.task.Task;
import ys.prototype.fmtaq.command.domain.task.TaskFactory;
import ys.prototype.fmtaq.command.domain.task.TaskRepository;
import ys.prototype.fmtaq.command.dto.TaskDTO;

@Service
@Transactional
public class TaskService {

    private final TaskFactory taskFactory;
    private final TaskRepository taskRepository;

    public TaskService(TaskFactory taskFactory, TaskRepository taskRepository) {
        this.taskFactory = taskFactory;
        this.taskRepository = taskRepository;
    }

    public void startTask(TaskDTO taskDTO) {
        Task task = taskFactory.createTask(taskDTO);
        taskRepository.save(task);
    }
}
