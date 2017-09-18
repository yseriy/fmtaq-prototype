package ys.prototype.fmtaq.application.assembler;

import org.springframework.stereotype.Component;
import ys.prototype.fmtaq.application.dto.CommandDTO;
import ys.prototype.fmtaq.domain.CommandStatus;
import ys.prototype.fmtaq.domain.TaskStatus;
import ys.prototype.fmtaq.domain.command.Command;
import ys.prototype.fmtaq.domain.command.Task;
import ys.prototype.fmtaq.domain.sequence.command.SequenceCommand;
import ys.prototype.fmtaq.domain.sequence.command.SequenceTask;

import java.util.*;

@Component
public class SequenceTaskAssembler {

    Task fromDTO(List<CommandDTO> commandDTOList) {
        SequenceTask sequenceTask = new SequenceTask(UUID.randomUUID(), TaskStatus.REGISTERED);
        Set<Command> commandSet = createSequenceCommandSet(sequenceTask, commandDTOList);
        sequenceTask.setCommandSet(commandSet);

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
                    commandDTO.getBody(), CommandStatus.REGISTERED, sequenceTask);
            commandList.add(nextCommand);
        }

        sequenceTask.setFirstCommandId(currentCommandId);

        return commandList;
    }
}
