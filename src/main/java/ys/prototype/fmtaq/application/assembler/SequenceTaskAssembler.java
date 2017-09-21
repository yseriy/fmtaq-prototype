package ys.prototype.fmtaq.application.assembler;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import ys.prototype.fmtaq.application.dto.CommandDTO;
import ys.prototype.fmtaq.application.dto.TaskDTO;
import ys.prototype.fmtaq.domain.CommandStatus;
import ys.prototype.fmtaq.domain.TaskStatus;
import ys.prototype.fmtaq.domain.sequencetask.SequenceCommand;
import ys.prototype.fmtaq.domain.sequencetask.SequenceTask;
import ys.prototype.fmtaq.domain.task.Command;
import ys.prototype.fmtaq.domain.task.CommandSender;
import ys.prototype.fmtaq.domain.task.Task;

import java.util.*;

@Component
public class SequenceTaskAssembler {

    private final CommandSender sendService;

    public SequenceTaskAssembler(@Qualifier(value = "commandAmqpSender") CommandSender sendService) {
        this.sendService = sendService;
    }

    Task fromDTO(TaskDTO taskDTO) {
        SequenceTask sequenceTask = new SequenceTask(UUID.randomUUID(), TaskStatus.REGISTERED, sendService);
        sequenceTask.setCommandSet(createSequenceCommandSet(sequenceTask, taskDTO.getCommandList()));

        return sequenceTask;
    }

    private Set<Command> createSequenceCommandSet(SequenceTask sequenceTask, List<CommandDTO> commandDTOList) {
        Set<Command> commandList = new HashSet<>();
        ListIterator<CommandDTO> commandDTOIterator = commandDTOList.listIterator(commandDTOList.size());
        UUID currentCommandId = null;
        SequenceCommand nextCommand = null;

        while (commandDTOIterator.hasPrevious()) {
            CommandDTO commandDTO = commandDTOIterator.previous();
            currentCommandId = UUID.randomUUID();
            nextCommand = new SequenceCommand(currentCommandId, nextCommand, commandDTO.getAddress(),
                    commandDTO.getBody(), CommandStatus.REGISTERED, sequenceTask, sendService);
            commandList.add(nextCommand);
        }

        sequenceTask.setFirstCommandId(currentCommandId);

        return commandList;
    }
}
