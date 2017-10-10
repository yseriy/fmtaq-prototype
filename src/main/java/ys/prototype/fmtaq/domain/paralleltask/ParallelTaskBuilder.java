package ys.prototype.fmtaq.domain.paralleltask;

import ys.prototype.fmtaq.domain.CommandStatus;
import ys.prototype.fmtaq.domain.TaskStatus;
import ys.prototype.fmtaq.domain.task.CommandSender;
import ys.prototype.fmtaq.domain.task.Task;
import ys.prototype.fmtaq.domain.task.TaskBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ParallelTaskBuilder implements TaskBuilder {

    private final List<ParallelCommand> parallelCommandList = new ArrayList<>();
    private final CommandSender commandSender;

    public ParallelTaskBuilder(CommandSender commandSender) {
        this.commandSender = commandSender;
    }

    @Override
    public Task build() {
        ParallelTask parallelTask = new ParallelTask(UUID.randomUUID(), TaskStatus.REGISTERED, commandSender);
        parallelTask.loadCommandList(parallelCommandList);

        return parallelTask;
    }

    @Override
    public void addCommand(String address, String body) {
        ParallelCommand parallelCommand = new ParallelCommand(UUID.randomUUID(), address, body,
                CommandStatus.REGISTERED, commandSender);
        parallelCommandList.add(parallelCommand);
    }
}
