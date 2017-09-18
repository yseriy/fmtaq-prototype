package ys.prototype.fmtaq.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ys.prototype.fmtaq.application.assembler.TaskAssembler;
import ys.prototype.fmtaq.application.dto.TaskDTO;
import ys.prototype.fmtaq.domain.command.Task;
import ys.prototype.fmtaq.domain.command.TaskRepository;

@Service
@Transactional
public class TaskService {

    private final TaskAssembler taskAssembler;
    private final TaskRepository taskRepository;

    public TaskService(TaskAssembler taskAssembler, TaskRepository taskRepository) {
        this.taskAssembler = taskAssembler;
        this.taskRepository = taskRepository;
    }

    public void startTask(TaskDTO taskDTO) {
        Task task = taskAssembler.fromDTO(taskDTO);
        taskRepository.save(task);
        task.start();
    }
}
