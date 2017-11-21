package ys.prototype.fmtaq.command.domain.sequencetask;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ys.prototype.fmtaq.command.domain.CommandStatus;
import ys.prototype.fmtaq.command.domain.TaskStatus;
import ys.prototype.fmtaq.command.domain.task.Command;
import ys.prototype.fmtaq.command.domain.task.CommandSender;

import javax.persistence.*;
import java.util.UUID;

@Setter
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class SequenceCommand extends Command {

    @OneToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name = "next_command_id", referencedColumnName = "id")
    private SequenceCommand nextCommand;

    SequenceCommand(UUID id, String address, String body, CommandSender commandSender) {
        super(id, address, body, commandSender);
    }

    @Override
    protected void handleOkResponse() {
        setCommandStatus(CommandStatus.OK);

        if (isLastCommand()) {
            getTask().setTaskStatus(TaskStatus.OK);
        } else {
            getCommandSender().send(nextCommand);
        }
    }

    @Override
    protected void handleErrorResponse() {
        setCommandStatus(CommandStatus.ERROR);
        getTask().setTaskStatus(TaskStatus.ERROR);
    }

    private Boolean isLastCommand() {
        return getNextCommand() == null;
    }
}
