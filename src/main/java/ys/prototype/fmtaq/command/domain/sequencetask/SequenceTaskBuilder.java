package ys.prototype.fmtaq.command.domain.sequencetask;

import ys.prototype.fmtaq.command.domain.task.CommandSender;
import ys.prototype.fmtaq.command.domain.task.Task;
import ys.prototype.fmtaq.command.domain.task.TaskBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class SequenceTaskBuilder implements TaskBuilder {

    private final List<SequenceCommand> sequenceCommandList = new ArrayList<>();
    private final CommandSender commandSender;
    private String account;
    private String serviceType;


    public SequenceTaskBuilder(CommandSender commandSender) {
        this.commandSender = commandSender;
    }

    @Override
    public Task build() {
        SequenceTask sequenceTask = new SequenceTask(UUID.randomUUID(), account, serviceType, commandSender);
        sequenceTask.loadCommandList(sequenceCommandList);

        return sequenceTask;
    }

    @Override
    public TaskBuilder setAccount(String account) {
        this.account = account;
        return this;
    }

    @Override
    public TaskBuilder setServiceType(String serviceType) {
        this.serviceType = serviceType;
        return this;
    }

    @Override
    public void addCommand(String address, String body) {
        SequenceCommand sequenceCommand = new SequenceCommand(UUID.randomUUID(), address, body, commandSender);
        sequenceCommandList.add(sequenceCommand);
    }
}
