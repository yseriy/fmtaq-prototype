package ys.prototype.fmtaq.command.domain.task.group;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ys.prototype.fmtaq.command.domain.ResponseStatus;
import ys.prototype.fmtaq.command.domain.task.Command;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.util.UUID;

@Setter
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class CommandGroup extends Command {

    @ManyToOne
    private TaskGroup task;

    public CommandGroup(UUID id, String address, String body) {
        super(id, address, body);
    }

    @Override
    public UUID updateStatusAndGetNextCommandId(ResponseStatus responseStatus) {
        setCommandStatus(responseStatus);
        getTask().decreaseCommandCounter();

        if (isLastCommand()) {
            getTask().setTaskEndStatus(responseStatus);
        }

        return null;
    }

    private Boolean isLastCommand() {
        return getTask().isLastCommand();
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "-" + getId();
    }
}
