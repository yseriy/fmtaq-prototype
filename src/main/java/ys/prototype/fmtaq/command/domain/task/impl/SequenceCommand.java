package ys.prototype.fmtaq.command.domain.task.impl;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ys.prototype.fmtaq.command.domain.CommandReturnStatus;
import ys.prototype.fmtaq.command.domain.task.Command;
import ys.prototype.fmtaq.command.domain.task.CommandStatus;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.util.UUID;

@Setter
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class SequenceCommand extends Command {

    private UUID nextCommandId;

    @ManyToOne
    private SequenceTask sequenceTask;

    public SequenceCommand(UUID id, UUID nextCommandId, String address, String body, SequenceTask task) {
        super(id, address, body);
        setNextCommandId(nextCommandId);
        setSequenceTask(task);
    }

    @Override
    public void updateCommandStatus(CommandReturnStatus commandReturnStatus) {
        setCommandStatus(commandReturnStatus);
    }

    @Override
    public Boolean isLast() {
        return getNextCommandId() == null || getStatus() == CommandStatus.ERROR;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "-" + getId();
    }
}
