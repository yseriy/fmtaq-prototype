package ys.prototype.fmtaq.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ys.prototype.fmtaq.application.assembler.TaskAssembler;
import ys.prototype.fmtaq.application.dto.TaskDTO;
import ys.prototype.fmtaq.application.validator.TaskDTOValidator;
import ys.prototype.fmtaq.domain.task.Task;

@Service
@Transactional
public class TaskService {

    private final TaskDTOValidator taskDTOValidator;
    private final TaskAssembler taskAssembler;

    public TaskService(TaskDTOValidator taskDTOValidator, TaskAssembler taskAssembler) {
        this.taskDTOValidator = taskDTOValidator;
        this.taskAssembler = taskAssembler;
    }

    public void startTask(TaskDTO taskDTO) {
        taskDTOValidator.validate(taskDTO);
        Task task = taskAssembler.fromDTO(taskDTO);
        task.start();
    }
}
