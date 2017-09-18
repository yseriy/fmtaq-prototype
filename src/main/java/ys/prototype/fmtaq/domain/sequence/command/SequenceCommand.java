package ys.prototype.fmtaq.domain.sequence.command;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ys.prototype.fmtaq.domain.CommandStatus;
import ys.prototype.fmtaq.domain.TaskStatus;
import ys.prototype.fmtaq.domain.command.Command;
import ys.prototype.fmtaq.domain.command.Task;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import java.util.UUID;

@Setter
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class SequenceCommand extends Command {

    @OneToOne
    @JoinColumn(name = "next_command_id", referencedColumnName = "id")
    private SequenceCommand nextCommand;

    public SequenceCommand(UUID id, SequenceCommand nextCommand, String address, String body,
                           CommandStatus commandStatus, Task task) {
        super(id, address, body, commandStatus, task);
        this.nextCommand = nextCommand;
    }

    @Override
    protected void handleOkResponse() {
        setCommandStatus(CommandStatus.OK);

        if (isLastCommand()) {
            getTask().setTaskStatus(TaskStatus.OK);
        } else {
            sendCommand(nextCommand);
        }
    }

    @Override
    protected void handleErrorResponse() {
        setCommandStatus(CommandStatus.ERROR);
        getTask().setTaskStatus(TaskStatus.ERROR);
    }

    private Boolean isLastCommand() {
        return nextCommand == null;
    }

    private void sendCommand(Command command) {
        if (getSendService() == null) {
            throw new RuntimeException("SendService not defined.");
        }

        getSendService().sendCommand(command);
    }
}
