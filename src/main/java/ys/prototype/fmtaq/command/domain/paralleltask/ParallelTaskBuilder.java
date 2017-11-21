package ys.prototype.fmtaq.command.domain.paralleltask;

import ys.prototype.fmtaq.command.domain.task.CommandSender;
import ys.prototype.fmtaq.command.domain.task.Task;
import ys.prototype.fmtaq.command.domain.task.TaskBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ParallelTaskBuilder implements TaskBuilder {

    private final List<ParallelCommand> parallelCommandList = new ArrayList<>();
    private final CommandSender commandSender;
    private String account;
    private String serviceType;

    public ParallelTaskBuilder(CommandSender commandSender) {
        this.commandSender = commandSender;
    }

    @Override
    public Task build() {
        ParallelTask parallelTask = new ParallelTask(UUID.randomUUID(), account, serviceType, commandSender);
        parallelTask.loadCommandList(parallelCommandList);

        return parallelTask;
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
        ParallelCommand parallelCommand = new ParallelCommand(UUID.randomUUID(), address, body, commandSender);
        parallelCommandList.add(parallelCommand);
    }
}
