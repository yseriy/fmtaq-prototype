package ys.prototype.fmtaq.application.assembler;

import org.springframework.stereotype.Component;
import ys.prototype.fmtaq.application.dto.TaskDTO;
import ys.prototype.fmtaq.domain.task.Task;
import ys.prototype.fmtaq.domain.task.TaskRepository;

@Component
public class TaskAssembler {

    private final SequenceTaskAssembler sequenceTaskAssembler;
    private final ParallelTaskAssembler parallelTaskAssembler;
    private final TaskRepository taskRepository;

    public TaskAssembler(SequenceTaskAssembler sequenceTaskAssembler, ParallelTaskAssembler parallelTaskAssembler,
                         TaskRepository taskRepository) {
        this.sequenceTaskAssembler = sequenceTaskAssembler;
        this.parallelTaskAssembler = parallelTaskAssembler;
        this.taskRepository = taskRepository;
    }

    public Task fromDTO(TaskDTO taskDTO) {
        Task task;

        switch (taskDTO.getType()) {
            case SEQUENCE:
                task = sequenceTaskAssembler.fromDTO(taskDTO);
                break;
            case GROUP:
                task = parallelTaskAssembler.fromDTO(taskDTO);
                break;
            default:
                throw new RuntimeException("unknown task type: " + taskDTO.getType());
        }

        taskRepository.save(task);

        return task;
    }
}
