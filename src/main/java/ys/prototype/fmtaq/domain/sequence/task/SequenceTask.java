package ys.prototype.fmtaq.domain.sequence.task;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ys.prototype.fmtaq.domain.TaskStatus;
import ys.prototype.fmtaq.domain.task.Command;
import ys.prototype.fmtaq.domain.task.CommandSender;
import ys.prototype.fmtaq.domain.task.Task;

import javax.persistence.Entity;
import java.util.UUID;

@Setter
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class SequenceTask extends Task {

    private UUID firstCommandId;

    public SequenceTask(UUID id, TaskStatus taskStatus, CommandSender commandSender) {
        super(id, taskStatus, commandSender);
    }

    @Override
    public void start() {
        Command firstCommand = getCommandSet().stream().filter(this::isFirstCommand).findFirst()
                .orElseThrow(this::exceptionSupplier);

        if (getCommandSet() == null) {
            throw new RuntimeException("SendService not defined.");
        }

        this.getCommandSender().send(firstCommand);
    }

    private Boolean isFirstCommand(Command command) {
        return command.getId() == firstCommandId;
    }

    private RuntimeException exceptionSupplier() {
        return new RuntimeException("cannot find first command.");
    }
}
