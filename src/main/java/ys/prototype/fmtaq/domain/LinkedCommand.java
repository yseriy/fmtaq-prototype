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
public class LinkedCommand implements Command {

    @Id
    private UUID id;
    private String address;
    private String body;
    private UUID nextCommandId;

    @Enumerated(EnumType.STRING)
    private CommandStatus status = CommandStatus.REGISTERED;

    @ManyToOne(cascade = CascadeType.PERSIST)
    private Sequence task;

    public LinkedCommand(UUID id, UUID nextCommandId, String address, String body, Sequence task) {
        this.id = id;
        this.address = address;
        this.body = body;
        this.nextCommandId = nextCommandId;
        this.task = task;
    }

    @Override
    public UUID updateTaskStatusAndGetNextCommandId(CommandResponseStatus commandResponseStatus) {
        setTaskState(commandResponseStatus);

        if (isLastCommand()) {
            setTaskEndStatus(commandResponseStatus);
            return null;
        } else {
            return getNextCommandId();
        }
    }

    private void setTaskState(CommandResponseStatus commandResponseStatus) {
        switch (commandResponseStatus) {
            case OK:
                setStatus(CommandStatus.OK);
                break;
            case ERROR:
                setStatus(CommandStatus.ERROR);
                getTask().setStatus(TaskStatus.ERROR);
                break;
            default:
                throw new RuntimeException("unknown command response status: " + commandResponseStatus);
        }
    }

    private Boolean isLastCommand() {
        return getNextCommandId() == null || getTask().getStatus() == TaskStatus.ERROR;
    }

    private void setTaskEndStatus(CommandResponseStatus commandResponseStatus) {
        switch (commandResponseStatus) {
            case OK:
                getTask().setStatus(TaskStatus.OK);
                break;
            case ERROR:
                break;
            default:
                throw new RuntimeException("unknown command response status: " + commandResponseStatus);
        }
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "-" + getId();
    }
}
