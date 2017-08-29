package ys.prototype.fmtaq.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.UUID;

@Setter
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class SequenceMetadata implements TaskMetadata {

    @Id
    @GeneratedValue
    private UUID id;

    @OneToOne
    private Command command;
    private UUID nextCommandId;

    @Version
    private Long version;

    public SequenceMetadata(Command command, UUID nextCommandId) {
        this.command = command;
        this.nextCommandId = nextCommandId;
    }

    @Override
    public UUID updateTaskStateAndGetNextCommandId(ResponseStatus responseStatus) {
        setCommandStatus(responseStatus);

        if (isLastCommand()) {
            setTaskEndStatus();
            return null;
        } else {
            return getNextCommandId();
        }
    }

    private void setCommandStatus(ResponseStatus responseStatus) {
        switch (responseStatus) {
            case OK:
                getCommand().setStatus(CommandStatus.OK);
                break;
            case ERROR:
                getCommand().setStatus(CommandStatus.ERROR);
                break;
            default:
                throw new RuntimeException("unknown command response status: " + responseStatus);
        }
    }

    private Boolean isLastCommand() {
        return getNextCommandId() == null || getCommand().getStatus() == CommandStatus.ERROR;
    }

    private void setTaskEndStatus() {
        if (getCommand().getStatus() == CommandStatus.OK) {
            getCommand().getTask().setStatus(TaskStatus.OK);
        } else {
            getCommand().getTask().setStatus(TaskStatus.ERROR);
        }
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "-" + getId();
    }
}
