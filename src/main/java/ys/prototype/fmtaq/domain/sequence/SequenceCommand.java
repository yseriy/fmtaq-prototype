package ys.prototype.fmtaq.domain.sequence;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ys.prototype.fmtaq.domain.Command;
import ys.prototype.fmtaq.domain.CommandStatus;
import ys.prototype.fmtaq.domain.Task;
import ys.prototype.fmtaq.domain.TaskStatus;

import javax.persistence.Entity;
import java.util.UUID;

@Setter
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class SequenceCommand extends Command {

    private UUID nextCommandId;

    public SequenceCommand(UUID id, UUID nextCommandId, String address, String body, CommandStatus commandStatus,
                           Task task) {
        super(id, address, body, commandStatus, task);
        this.nextCommandId = nextCommandId;
    }

    @Override
    protected void handleOkResponse() {
        if (isLastCommand()) {
            getTask().setTaskStatus(TaskStatus.OK);
        } else {
            Command command = findCommandById(nextCommandId);
            sendCommand(command);
        }
    }

    @Override
    protected void handleErrorResponse() {
        getTask().setTaskStatus(TaskStatus.ERROR);
    }

    private Boolean isLastCommand() {
        return nextCommandId == null;
    }

    private Command findCommandById(UUID nextCommandId) {
        checkInfrastructureService();
        Command command = getInfrastructureService().findCommandById(nextCommandId);

        if (command == null) {
            throw new RuntimeException("cannot find next command. id: " + nextCommandId);
        }

        return command;
    }

    private void sendCommand(Command command) {
        checkInfrastructureService();
        getInfrastructureService().sendCommand(command);
    }

    private void checkInfrastructureService() {
        if (getInfrastructureService() == null) {
            throw new RuntimeException("InfrastructureService not defined.");
        }
    }
}
