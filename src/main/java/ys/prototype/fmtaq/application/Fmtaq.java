package ys.prototype.fmtaq.application;

import org.springframework.stereotype.Component;
import ys.prototype.fmtaq.application.dto.CommandDTO;
import ys.prototype.fmtaq.application.dto.TaskDTO;
import ys.prototype.fmtaq.domain.command.Command;
import ys.prototype.fmtaq.domain.parallelcommand.ParallelCommandFactory;
import ys.prototype.fmtaq.domain.parallelcommand.ParallelTask;
import ys.prototype.fmtaq.domain.sequencecommand.SequenceCommand;
import ys.prototype.fmtaq.domain.sequencecommand.SequenceCommandFactory;
import ys.prototype.fmtaq.domain.sequencecommand.SequenceTask;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class Fmtaq {

    private final SequenceCommandFactory sequenceCommandFactory;
    private final ParallelCommandFactory parallelCommandFactory;

    public Fmtaq(SequenceCommandFactory sequenceCommandFactory, ParallelCommandFactory parallelCommandFactory) {
        this.sequenceCommandFactory = sequenceCommandFactory;
        this.parallelCommandFactory = parallelCommandFactory;
    }

    public List<Command> createCommand(TaskDTO taskDTO) {
        switch (taskDTO.getType()) {
            case SEQUENCE:
                return createSequenceCommandList(taskDTO.getCommandDTOList());
            case GROUP:
                return createGroupCommandList(taskDTO.getCommandDTOList());
            default:
                throw new RuntimeException("unknown task type: " + taskDTO.getType());
        }
    }

    private List<Command> createSequenceCommandList(List<CommandDTO> commandDTOList) {
        SequenceTask sequenceTask = sequenceCommandFactory.createTask();
        List<Command> commandList = new ArrayList<>();
        ListIterator<CommandDTO> commandDTOIterator = commandDTOList.listIterator(commandDTOList.size());
        SequenceCommand nextCommand = null;

        while (commandDTOIterator.hasPrevious()) {
            CommandDTO commandDTO = commandDTOIterator.previous();
            nextCommand = sequenceCommandFactory.createCommand(UUID.randomUUID(), nextCommand, commandDTO.getAddress(),
                    commandDTO.getBody(), sequenceTask);
            commandList.add(nextCommand);
        }

        Collections.reverse(commandList);

        return commandList;
    }

    private List<Command> createGroupCommandList(List<CommandDTO> commandDTOList) {
        ParallelTask parallelTask = parallelCommandFactory.createTask(commandDTOList.size());
        return commandDTOList.stream().map(commandDTO -> createGroupCommand(commandDTO, parallelTask))
                .collect(Collectors.toList());
    }

    private Command createGroupCommand(CommandDTO commandDTO, ParallelTask parallelTask) {
        return parallelCommandFactory.createCommand(UUID.randomUUID(), commandDTO.getAddress(), commandDTO.getBody(),
                parallelTask);
    }
}
