package ys.prototype.fmtaq.domain.singletask;

import ys.prototype.fmtaq.domain.task.CommandSender;
import ys.prototype.fmtaq.domain.task.Task;
import ys.prototype.fmtaq.domain.task.TaskBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class SingleTaskBuilder implements TaskBuilder {

    private final List<SingleCommand> singleCommandList = new ArrayList<>();
    private final CommandSender commandSender;
    private String account;
    private String serviceType;

    public SingleTaskBuilder(CommandSender commandSender) {
        this.commandSender = commandSender;
    }

    @Override
    public Task build() {
        SingleTask singleTask = new SingleTask(UUID.randomUUID(), account, serviceType, commandSender);
        singleTask.loadCommandList(singleCommandList);

        return singleTask;
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
        SingleCommand singleCommand = new SingleCommand(UUID.randomUUID(), address, body, commandSender);
        singleCommandList.add(singleCommand);
    }
}
