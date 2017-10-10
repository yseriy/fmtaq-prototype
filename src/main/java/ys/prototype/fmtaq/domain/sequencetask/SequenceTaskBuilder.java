package ys.prototype.fmtaq.domain.sequencetask;

import ys.prototype.fmtaq.domain.CommandStatus;
import ys.prototype.fmtaq.domain.TaskStatus;
import ys.prototype.fmtaq.domain.task.CommandSender;
import ys.prototype.fmtaq.domain.task.Task;
import ys.prototype.fmtaq.domain.task.TaskBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class SequenceTaskBuilder implements TaskBuilder {

    private final List<SequenceCommand> sequenceCommandList = new ArrayList<>();
    private final CommandSender commandSender;

    public SequenceTaskBuilder(CommandSender commandSender) {
        this.commandSender = commandSender;
    }

    @Override
    public Task build() {
        SequenceTask sequenceTask = new SequenceTask(UUID.randomUUID(), TaskStatus.REGISTERED, commandSender);
        sequenceTask.loadCommandList(sequenceCommandList);

        return sequenceTask;
    }

    @Override
    public void addCommand(String address, String body) {
        SequenceCommand sequenceCommand = new SequenceCommand(UUID.randomUUID(), address, body,
                CommandStatus.REGISTERED, commandSender);
        sequenceCommandList.add(sequenceCommand);
    }
}
