package ys.prototype.fmtaq.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ys.prototype.fmtaq.application.assembler.TaskAssembler;
import ys.prototype.fmtaq.application.assembler.TaskIdAssembler;
import ys.prototype.fmtaq.application.dto.TaskDTO;
import ys.prototype.fmtaq.application.dto.TaskIdDTO;
import ys.prototype.fmtaq.application.validator.TaskDTOValidator;
import ys.prototype.fmtaq.domain.task.Task;

@Service
@Transactional
public class TaskService {

    private final TaskDTOValidator taskDTOValidator;
    private final TaskAssembler taskAssembler;
    private final TaskIdAssembler taskIdAssembler;

    public TaskService(TaskDTOValidator taskDTOValidator, TaskAssembler taskAssembler, TaskIdAssembler taskIdAssembler) {
        this.taskDTOValidator = taskDTOValidator;
        this.taskAssembler = taskAssembler;
        this.taskIdAssembler = taskIdAssembler;
    }

    public TaskIdDTO startAsyncTask(TaskDTO taskDTO) {
        taskDTOValidator.validate(taskDTO);
        Task task = taskAssembler.fromDTO(taskDTO);
        task.start();

        return taskIdAssembler.toDTO(task.getId());
    }
}
