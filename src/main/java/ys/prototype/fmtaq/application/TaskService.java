package ys.prototype.fmtaq.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ys.prototype.fmtaq.application.assembler.TaskAssembler;
import ys.prototype.fmtaq.application.assembler.TaskIdAssembler;
import ys.prototype.fmtaq.application.dto.TaskDTO;
import ys.prototype.fmtaq.application.dto.TaskIdDTO;
import ys.prototype.fmtaq.domain.task.Task;
import ys.prototype.fmtaq.domain.task.TaskRepository;

@Service
@Transactional
public class TaskService {

    private final TaskAssembler taskAssembler;
    private final TaskIdAssembler taskIdAssembler;
    private final TaskRepository taskRepository;

    public TaskService(TaskAssembler taskAssembler, TaskIdAssembler taskIdAssembler, TaskRepository taskRepository) {
        this.taskAssembler = taskAssembler;
        this.taskIdAssembler = taskIdAssembler;
        this.taskRepository = taskRepository;
    }

    public TaskIdDTO startAsyncTask(TaskDTO taskDTO) {
        Task task = taskAssembler.fromDTO(taskDTO);
        taskRepository.save(task);
        task.start();

        return taskIdAssembler.toDTO(task.getId());
    }
}
