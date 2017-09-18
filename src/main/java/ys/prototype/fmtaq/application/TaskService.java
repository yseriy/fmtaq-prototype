package ys.prototype.fmtaq.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ys.prototype.fmtaq.application.assembler.TaskAssembler;
import ys.prototype.fmtaq.application.dto.TaskDTO;
import ys.prototype.fmtaq.domain.task.Task;

@Service
@Transactional
public class TaskService {

    private final TaskAssembler taskAssembler;

    public TaskService(TaskAssembler taskAssembler) {
        this.taskAssembler = taskAssembler;
    }

    public void startTask(TaskDTO taskDTO) {
        Task task = taskAssembler.fromDTO(taskDTO);
        task.start();
    }
}
