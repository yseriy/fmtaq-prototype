package ys.prototype.fmtaq.application.assembler;

import org.springframework.stereotype.Component;
import ys.prototype.fmtaq.application.dto.TaskDTO;
import ys.prototype.fmtaq.domain.command.Task;

@Component
public class TaskAssembler {

    private final SequenceTaskAssembler sequenceTaskAssembler;
    private final ParallelTaskAssembler parallelTaskAssembler;

    public TaskAssembler(SequenceTaskAssembler sequenceTaskAssembler, ParallelTaskAssembler parallelTaskAssembler) {
        this.sequenceTaskAssembler = sequenceTaskAssembler;
        this.parallelTaskAssembler = parallelTaskAssembler;
    }

    public Task fromDTO(TaskDTO taskDTO) {
        switch (taskDTO.getType()) {
            case SEQUENCE:
                return sequenceTaskAssembler.fromDTO(taskDTO.getCommandDTOList());
            case GROUP:
                return parallelTaskAssembler.fromDTO(taskDTO.getCommandDTOList());
            default:
                throw new RuntimeException("unknown task type: " + taskDTO.getType());
        }
    }
}
