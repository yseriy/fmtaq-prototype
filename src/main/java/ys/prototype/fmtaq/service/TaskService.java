package ys.prototype.fmtaq.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ys.prototype.fmtaq.domain.Task;
import ys.prototype.fmtaq.domain.TaskConverter;
import ys.prototype.fmtaq.repository.TaskRepository;

import java.util.UUID;

@Service
@Transactional
public class TaskService {

    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public UUID registerTask(TaskConverter converter) {
        Task task = converter.getTask();
        taskRepository.save(task);

        return task.getId();
    }
}
