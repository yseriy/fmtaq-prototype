package ys.prototype.fmtaq.domain.singletask;

import ys.prototype.fmtaq.domain.CommandStatus;
import ys.prototype.fmtaq.domain.TaskStatus;
import ys.prototype.fmtaq.domain.task.CommandSender;
import ys.prototype.fmtaq.domain.task.Task;
import ys.prototype.fmtaq.domain.task.TaskBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class SingleTaskBuilder implements TaskBuilder {

    private final List<SingleCommand> singleCommandList = new ArrayList<>();
    private final CommandSender commandSender;

    public SingleTaskBuilder(CommandSender commandSender) {
        this.commandSender = commandSender;
    }

    @Override
    public Task build() {
        SingleTask singleTask = new SingleTask(UUID.randomUUID(), TaskStatus.REGISTERED, commandSender);
        singleTask.loadCommandList(singleCommandList);

        return singleTask;
    }

    @Override
    public void addCommand(String address, String body) {
        SingleCommand singleCommand = new SingleCommand(UUID.randomUUID(), address, body, CommandStatus.REGISTERED,
                commandSender);
        singleCommandList.add(singleCommand);
    }
}
