package ys.prototype.fmtaq.command.domain.singletask;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
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
public class SingleCommand extends Command {

    SingleCommand(UUID id, String address, String body, CommandSender commandSender) {
        super(id, address, body, commandSender);
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
