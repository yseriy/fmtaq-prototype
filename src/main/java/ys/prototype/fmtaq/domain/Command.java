package ys.prototype.fmtaq.domain;

import lombok.*;

import javax.persistence.*;
import java.util.UUID;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(exclude = "task")
@EqualsAndHashCode(exclude = "task")
@Entity
public class Command {

    @Id
    @GeneratedValue
    private UUID id;

    private String address;

    private String body;

    @Enumerated(EnumType.STRING)
    private CommandStatus status;

    private Integer step;

    @ManyToOne(cascade = CascadeType.PERSIST)
    private Task task;

    public Command(String address, String body, CommandStatus status, Integer step, Task task) {
        this.address = address;
        this.body = body;
        this.status = status;
        this.step = step;
        this.task = task;
    }

    public Boolean hasNextCommand() {
        return (getTask().getCommandCount() - getStep()) > 1 && getTask().hasNonFatalStatus();
    }

    public Integer nextStep() {
        return getStep() + 1;
    }

    public void setStatusFromResponse(CommandResponseStatus responseStatus) {
        if (responseStatus == CommandResponseStatus.OK) {
            setStatus(CommandStatus.OK);
        } else {
            setStatus(CommandStatus.ERROR);
        }

        if (hasNextCommand()) {
            updateTaskStatus(responseStatus);
        } else {
            updateEndTaskStatus(responseStatus);
        }
    }

    private void updateTaskStatus(CommandResponseStatus responseStatus) {
        if (responseStatus == CommandResponseStatus.OK) {
            getTask().setCommandSuccessStatus();
        } else {
            getTask().setCommandErrorStatus();
        }
    }

    private void updateEndTaskStatus(CommandResponseStatus responseStatus) {
        if (responseStatus == CommandResponseStatus.OK) {
            getTask().setLastCommandSuccessStatus();
        } else {
            getTask().setLastCommandErrorStatus();
        }
    }
}
