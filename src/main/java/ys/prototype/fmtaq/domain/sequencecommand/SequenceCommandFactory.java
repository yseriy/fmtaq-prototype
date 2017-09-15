package ys.prototype.fmtaq.domain.sequencecommand;

import org.springframework.stereotype.Component;
import ys.prototype.fmtaq.domain.CommandSendService;
import ys.prototype.fmtaq.domain.CommandStatus;
import ys.prototype.fmtaq.domain.TaskStatus;

import java.util.UUID;

@Component
public class SequenceCommandFactory {

    private final CommandSendService commandSendService;

    public SequenceCommandFactory(CommandSendService commandSendService) {
        this.commandSendService = commandSendService;
    }

    public SequenceTask createTask() {
        SequenceTask sequenceTask = new SequenceTask();
        sequenceTask.setId(UUID.randomUUID());
        sequenceTask.setTaskStatus(TaskStatus.REGISTERED);

        return sequenceTask;
    }

    public SequenceCommand createCommand(UUID id, SequenceCommand nextCommand, String address, String body,
                                         SequenceTask sequenceTask) {
        SequenceCommand sequenceCommand = new SequenceCommand();
        sequenceCommand.setId(id);
        sequenceCommand.setNextCommand(nextCommand);
        sequenceCommand.setAddress(address);
        sequenceCommand.setBody(body);
        sequenceCommand.setCommandStatus(CommandStatus.REGISTERED);
        sequenceCommand.setTask(sequenceTask);
        sequenceCommand.setSendService(commandSendService);

        return sequenceCommand;
    }
}
