package ys.prototype.fmtaq.domain.parallelcommand;

import org.springframework.stereotype.Component;
import ys.prototype.fmtaq.domain.CommandSendService;
import ys.prototype.fmtaq.domain.CommandStatus;
import ys.prototype.fmtaq.domain.TaskStatus;

import java.util.UUID;

@Component
public class ParallelCommandFactory {

    private final CommandSendService commandSendService;

    public ParallelCommandFactory(CommandSendService commandSendService) {
        this.commandSendService = commandSendService;
    }

    public ParallelTask createTask(Integer commandCounter) {
        ParallelTask parallelTask = new ParallelTask();
        parallelTask.setId(UUID.randomUUID());
        parallelTask.setTaskStatus(TaskStatus.REGISTERED);
        parallelTask.setCommandCounter(commandCounter);

        return parallelTask;
    }

    public ParallelCommand createCommand(UUID id, String address, String body, ParallelTask parallelTask) {
        ParallelCommand parallelCommand = new ParallelCommand();
        parallelCommand.setId(id);
        parallelCommand.setAddress(address);
        parallelCommand.setBody(body);
        parallelCommand.setCommandStatus(CommandStatus.REGISTERED);
        parallelCommand.setTask(parallelTask);
        parallelCommand.setSendService(commandSendService);

        return parallelCommand;
    }
}
