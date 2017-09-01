package ys.prototype.fmtaq.command.domain.task;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ys.prototype.fmtaq.command.domain.ResponseStatus;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.util.UUID;

@Setter
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class LinkedCommand extends Command {

    private UUID nextCommandId;

    @ManyToOne
    private TaskSequence task;

    public LinkedCommand(UUID id, UUID nextCommandId, String address, String body) {
        super(id, address, body);
        setNextCommandId(nextCommandId);
    }

    @Override
    public UUID updateStatusAndGetNextCommandId(ResponseStatus responseStatus) {
        setCommandStatus(responseStatus);

        if (isLastCommand()) {
            setTaskEndStatus(responseStatus);
            return null;
        } else {
            return getNextCommandId();
        }
    }

    private Boolean isLastCommand() {
        return getNextCommandId() == null || getStatus() == CommandStatus.ERROR;
    }

    private void setTaskEndStatus(ResponseStatus responseStatus) {
        switch (responseStatus) {
            case OK:
                getTask().setStatus(TaskStatus.OK);
                break;
            case ERROR:
                getTask().setStatus(TaskStatus.ERROR);
                break;
            default:
                throw new RuntimeException("unknown command response status: " + responseStatus);
        }
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "-" + getId();
    }
}
