package ys.prototype.fmtaq.command.domain.task.impl;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ys.prototype.fmtaq.command.domain.CommandReturnStatus;
import ys.prototype.fmtaq.command.domain.task.Command;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.util.UUID;

@Setter
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class ParallelCommand extends Command {

    @ManyToOne
    private ParallelTask parallelTask;

    public ParallelCommand(UUID id, String address, String body, ParallelTask task) {
        super(id, address, body);
        setParallelTask(task);
    }

    @Override
    public void updateCommandStatus(CommandReturnStatus commandReturnStatus) {
        getParallelTask().decreaseCommandCounter();
        setCommandStatus(commandReturnStatus);
    }

    @Override
    public Boolean isLast() {
        return getParallelTask().getCommandCounter() <= 0;
    }

    @Override
    public UUID getNextCommandId() {
        return null;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "-" + getId();
    }
}
