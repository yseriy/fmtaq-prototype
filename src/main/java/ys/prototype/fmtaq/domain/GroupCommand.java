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
public class GroupCommand implements Command {

    @Id
    private UUID id;
    private String address;
    private String body;

    @Enumerated(EnumType.STRING)
    private CommandStatus status = CommandStatus.REGISTERED;

    @ManyToOne(cascade = CascadeType.PERSIST)
    private Group task;

    public GroupCommand(UUID id, String address, String body, Group task) {
        this.id = id;
        this.address = address;
        this.body = body;
        this.task = task;
    }

    @Override
    public UUID updateTaskStatusAndGetNextCommandId(CommandResponseStatus commandResponseStatus) {
        setTaskState(commandResponseStatus);

        if (isLastCommand()) {
            setTaskEndStatus(commandResponseStatus);
        }

        return null;
    }

    private void setTaskState(CommandResponseStatus commandResponseStatus) {
        getTask().setCommandCounter(getTask().getCommandCounter() - 1);

        switch (commandResponseStatus) {
            case OK:
                setStatus(CommandStatus.OK);
                break;
            case ERROR:
                setStatus(CommandStatus.ERROR);
                getTask().setStatus(TaskStatus.HAS_ERROR);
                break;
            default:
                throw new RuntimeException("unknown command response status: " + commandResponseStatus);
        }
    }

    private Boolean isLastCommand() {
        return getTask().getCommandCounter() == 0;
    }

    private void setTaskEndStatus(CommandResponseStatus commandResponseStatus) {
        switch (commandResponseStatus) {
            case OK:
                setTaskEndStatusOk();
                break;
            case ERROR:
                getTask().setStatus(TaskStatus.PARTIAL);
                break;
            default:
                throw new RuntimeException("unknown command response status: " + commandResponseStatus);
        }
    }

    private void setTaskEndStatusOk() {
        switch (getTask().getStatus()) {
            case REGISTERED:
                getTask().setStatus(TaskStatus.OK);
                break;
            case HAS_ERROR:
                getTask().setStatus(TaskStatus.PARTIAL);
                break;
            default:
                throw new RuntimeException("unknown task status: " + getTask().getStatus());
        }
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "-" + getId();
    }
}
