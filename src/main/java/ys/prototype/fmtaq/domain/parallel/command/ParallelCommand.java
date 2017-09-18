package ys.prototype.fmtaq.domain.parallel.command;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ys.prototype.fmtaq.domain.CommandResponseStatus;
import ys.prototype.fmtaq.domain.CommandSendService;
import ys.prototype.fmtaq.domain.CommandStatus;
import ys.prototype.fmtaq.domain.TaskStatus;
import ys.prototype.fmtaq.domain.command.Command;
import ys.prototype.fmtaq.domain.command.Task;

import javax.persistence.Entity;
import java.util.UUID;

@Setter
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class ParallelCommand extends Command {

    public ParallelCommand(UUID id, String address, String body, CommandStatus commandStatus, Task task,
                           CommandSendService sendService) {
        super(id, address, body, commandStatus, task, sendService);
    }

    @Override
    public void handleResponse(CommandResponseStatus responseStatus) {
        reduceCommandCounter();
        super.handleResponse(responseStatus);
    }

    @Override
    protected void handleOkResponse() {
        setCommandStatus(CommandStatus.OK);

        if (isLastCommand()) {
            getParallelTask().setTaskStatus(TaskStatus.OK);
        }
    }

    @Override
    protected void handleErrorResponse() {
        setCommandStatus(CommandStatus.ERROR);

        if (isLastCommand()) {
            getParallelTask().setTaskStatus(TaskStatus.ERROR);
        }
    }

    private void reduceCommandCounter() {
        getParallelTask().setCommandCounter(getParallelTask().getCommandCounter() - 1);
    }

    private Boolean isLastCommand() {
        return getParallelTask().getCommandCounter() <= 0;
    }

    private ParallelTask getParallelTask() {
        return (ParallelTask) getTask();
    }
}
