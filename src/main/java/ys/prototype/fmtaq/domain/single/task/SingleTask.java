package ys.prototype.fmtaq.domain.single.task;

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
public class SingleTask extends Task {

    public SingleTask(UUID id, TaskStatus taskStatus, CommandSender commandSender) {
        super(id, taskStatus, commandSender);
    }

    @Override
    public void start() {
        if (getCommandSender() == null) {
            throw new RuntimeException("SendService not defined.");
        }

        Command command = getCommandSet().stream().findFirst().orElseThrow(this::exceptionSupplier);
        getCommandSender().send(command);
    }

    private RuntimeException exceptionSupplier() {
        return new RuntimeException("cannot get command for SingeTask: " + this);
    }
}
