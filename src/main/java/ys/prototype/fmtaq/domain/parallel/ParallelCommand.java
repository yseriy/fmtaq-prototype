package ys.prototype.fmtaq.domain.parallel;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ys.prototype.fmtaq.domain.*;

import javax.persistence.Entity;
import java.util.UUID;

@Setter
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class ParallelCommand extends Command {

    public ParallelCommand(UUID id, String address, String body, CommandStatus commandStatus, Task task) {
        super(id, address, body, commandStatus, task);
    }

    @Override
    public void handleResponse(CommandResponseStatus responseStatus) {
        reduceCommandCounter();
        super.handleResponse(responseStatus);
    }

    @Override
    protected void handleOkResponse() {
        if (isLastCommand()) {
            getParallelTask().setTaskStatus(TaskStatus.OK);
        }
    }

    @Override
    protected void handleErrorResponse() {
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
