package ys.prototype.fmtaq.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import java.util.UUID;

@Setter
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class GroupMetadata implements TaskMetadata{

    @Id
    @GeneratedValue
    private UUID id;
    private Integer commandCounter;

    @OneToOne
    private Command command;

    public GroupMetadata(Command command, Integer commandCounter) {
        this.command = command;
        this.commandCounter = commandCounter;
    }

    @Override
    public UUID updateTaskStateAndGetNextCommandId(ResponseStatus responseStatus) {
        setCommandCounter(getCommandCounter() - 1);

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

        if (getCommandCounter() == 0) {
            getCommand().getTask().setStatus(TaskStatus.OK);
        } else if (getCommandCounter() < 0) {
            throw new RuntimeException("negative command counter. group metadata: " + this);
        }

        return null;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "-" + getId();
    }
}
