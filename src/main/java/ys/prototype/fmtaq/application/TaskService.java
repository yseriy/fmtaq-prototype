package ys.prototype.fmtaq.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ys.prototype.fmtaq.application.assembler.TaskAssembler;
import ys.prototype.fmtaq.application.assembler.TaskIdAssembler;
import ys.prototype.fmtaq.application.dto.TaskDTO;
import ys.prototype.fmtaq.application.dto.TaskIdDTO;
import ys.prototype.fmtaq.domain.task.Task;

@Service
@Transactional
public class TaskService {

    private final TaskAssembler taskAssembler;
    private final TaskIdAssembler taskIdAssembler;

    public TaskService(TaskAssembler taskAssembler, TaskIdAssembler taskIdAssembler) {
        this.taskAssembler = taskAssembler;
        this.taskIdAssembler = taskIdAssembler;
    }

    public TaskIdDTO startAsyncTask(TaskDTO taskDTO) {
        Task task = taskAssembler.fromDTO(taskDTO);
        task.start();

        return taskIdAssembler.toDTO(task.getId());
    }
}
