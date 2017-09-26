package ys.prototype.fmtaq.application.assembler;

import org.springframework.stereotype.Component;
import ys.prototype.fmtaq.application.dto.TaskDTO;
import ys.prototype.fmtaq.domain.task.Task;

@Component
public class TaskAssembler {

    private final SingleTaskAssembler singleTaskAssembler;
    private final SequenceTaskAssembler sequenceTaskAssembler;
    private final ParallelTaskAssembler parallelTaskAssembler;

    public TaskAssembler(SingleTaskAssembler singleTaskAssembler, SequenceTaskAssembler sequenceTaskAssembler,
                         ParallelTaskAssembler parallelTaskAssembler) {
        this.singleTaskAssembler = singleTaskAssembler;
        this.sequenceTaskAssembler = sequenceTaskAssembler;
        this.parallelTaskAssembler = parallelTaskAssembler;
    }

    public Task fromDTO(TaskDTO taskDTO) {
        switch (taskDTO.getType()) {
            case "SINGLE":
                return singleTaskAssembler.fromDTO(taskDTO);
            case "SEQUENCE":
                return sequenceTaskAssembler.fromDTO(taskDTO);
            case "PARALLEL":
                return parallelTaskAssembler.fromDTO(taskDTO);
            default:
                throw unknownTaskType(taskDTO.getType());
        }
    }

    private RuntimeException unknownTaskType(String taskType) {
        String exceptionString = String.format("unknown task type: %s", taskType);
        return new RuntimeException(exceptionString);
    }
}
