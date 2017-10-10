package ys.prototype.fmtaq.domain.sequencetask;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ys.prototype.fmtaq.domain.CommandStatus;
import ys.prototype.fmtaq.domain.TaskStatus;
import ys.prototype.fmtaq.domain.task.Command;
import ys.prototype.fmtaq.domain.task.CommandSender;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import java.util.UUID;

@Setter
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class SequenceCommand extends Command {

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "next_command_id", referencedColumnName = "id")
    private SequenceCommand nextCommand;

    SequenceCommand(UUID id, String address, String body, CommandStatus commandStatus, CommandSender commandSender) {
        super(id, address, body, commandStatus, commandSender);
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
