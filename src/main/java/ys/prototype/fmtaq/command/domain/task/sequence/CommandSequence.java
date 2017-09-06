package ys.prototype.fmtaq.command.domain.task.sequence;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ys.prototype.fmtaq.command.domain.ResponseStatus;
import ys.prototype.fmtaq.command.domain.task.Command;
import ys.prototype.fmtaq.command.domain.task.CommandStatus;
import ys.prototype.fmtaq.command.domain.task.Task;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.util.UUID;

@Setter
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class CommandSequence extends Command {

    private UUID nextCommandId;

    @ManyToOne
    private Task task;

    public CommandSequence(UUID id, UUID nextCommandId, String address, String body) {
        super(id, address, body);
        setNextCommandId(nextCommandId);
    }

    @Override
    public UUID updateStatusAndGetNextCommandId(ResponseStatus responseStatus) {
        setCommandStatus(responseStatus);

        if (isLastCommand()) {
            getTask().setTaskEndStatus(responseStatus);
            return null;
        } else {
            return getNextCommandId();
        }
    }

    private Boolean isLastCommand() {
        return getNextCommandId() == null || getStatus() == CommandStatus.ERROR;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "-" + getId();
    }
}
