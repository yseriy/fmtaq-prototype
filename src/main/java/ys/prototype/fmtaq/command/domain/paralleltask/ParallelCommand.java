package ys.prototype.fmtaq.command.domain.paralleltask;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ys.prototype.fmtaq.command.domain.CommandResponseStatus;
import ys.prototype.fmtaq.command.domain.CommandStatus;
import ys.prototype.fmtaq.command.domain.TaskStatus;
import ys.prototype.fmtaq.command.domain.task.Command;
import ys.prototype.fmtaq.command.domain.task.CommandSender;

import javax.persistence.Entity;
import java.util.UUID;

@Setter
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class ParallelCommand extends Command {

    ParallelCommand(UUID id, String address, String body, CommandSender commandSender) {
        super(id, address, body, commandSender);
    }

    @Override
    public void handleResponse(CommandResponseStatus responseStatus) {
        getParallelTask().reduceCommandCounter();
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

    private Boolean isLastCommand() {
        return getParallelTask().getCommandCounter() == 0;
    }

    private ParallelTask getParallelTask() {
        return (ParallelTask) getTask();
    }
}
