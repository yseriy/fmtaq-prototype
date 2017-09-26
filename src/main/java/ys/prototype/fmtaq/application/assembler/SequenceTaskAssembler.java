package ys.prototype.fmtaq.application.assembler;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import ys.prototype.fmtaq.application.dto.CommandDTO;
import ys.prototype.fmtaq.application.dto.TaskDTO;
import ys.prototype.fmtaq.domain.CommandStatus;
import ys.prototype.fmtaq.domain.TaskStatus;
import ys.prototype.fmtaq.domain.sequencetask.SequenceCommand;
import ys.prototype.fmtaq.domain.sequencetask.SequenceTask;
import ys.prototype.fmtaq.domain.task.CommandSender;
import ys.prototype.fmtaq.domain.task.Task;

import java.util.*;

@Component
public class SequenceTaskAssembler {

    private final static Integer FIRST_ELEMENT = 0;
    private final CommandSender sendService;

    public SequenceTaskAssembler(@Qualifier(value = "commandAmqpSender") CommandSender sendService) {
        this.sendService = sendService;
    }

    Task fromDTO(TaskDTO taskDTO) {
        SequenceTask sequenceTask = createSequenceTask();
        List<SequenceCommand> sequenceCommandList = createSequenceCommandList(sequenceTask, taskDTO.getCommandList());
        sequenceTask.setFirstCommand(sequenceCommandList.get(FIRST_ELEMENT));
        sequenceTask.setCommandSet(new HashSet<>(sequenceCommandList));

        return sequenceTask;
    }

    private SequenceTask createSequenceTask() {
        return new SequenceTask(UUID.randomUUID(), TaskStatus.REGISTERED, sendService);
    }

    private List<SequenceCommand> createSequenceCommandList(SequenceTask sequenceTask, List<CommandDTO> commandDTOList) {
        List<SequenceCommand> sequenceCommandList = new ArrayList<>();
        ListIterator<CommandDTO> commandDTOIterator = commandDTOList.listIterator(commandDTOList.size());
        SequenceCommand nextCommand = null;

        while (commandDTOIterator.hasPrevious()) {
            CommandDTO commandDTO = commandDTOIterator.previous();
            nextCommand = createSequenceCommand(sequenceTask, commandDTO, nextCommand);
            sequenceCommandList.add(nextCommand);
        }

        Collections.reverse(sequenceCommandList);
        return sequenceCommandList;
    }

    private SequenceCommand createSequenceCommand(SequenceTask sequenceTask, CommandDTO commandDTO,
                                                  SequenceCommand nextCommand) {
        return new SequenceCommand(UUID.randomUUID(), nextCommand, commandDTO.getAddress(), commandDTO.getBody(),
                CommandStatus.REGISTERED, sequenceTask, sendService);
    }
}
