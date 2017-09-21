package ys.prototype.fmtaq.domain.singletask;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ys.prototype.fmtaq.domain.CommandStatus;
import ys.prototype.fmtaq.domain.TaskStatus;
import ys.prototype.fmtaq.domain.task.Command;
import ys.prototype.fmtaq.domain.task.CommandSender;
import ys.prototype.fmtaq.domain.task.Task;

import javax.persistence.Entity;
import java.util.UUID;

@Setter
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class SingleCommand extends Command {

    public SingleCommand(UUID id, String address, String body, CommandStatus commandStatus, Task task,
                         CommandSender commandSender) {
        super(id, address, body, commandStatus, task, commandSender);
    }

    @Override
    protected void handleOkResponse() {
        setCommandStatus(CommandStatus.OK);
        getTask().setTaskStatus(TaskStatus.OK);
    }

    @Override
    protected void handleErrorResponse() {
        setCommandStatus(CommandStatus.ERROR);
        getTask().setTaskStatus(TaskStatus.ERROR);
    }
}
