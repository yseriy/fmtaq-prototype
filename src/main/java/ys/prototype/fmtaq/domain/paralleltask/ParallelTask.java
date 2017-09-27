package ys.prototype.fmtaq.domain.paralleltask;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ys.prototype.fmtaq.domain.TaskStatus;
import ys.prototype.fmtaq.domain.task.CommandSender;
import ys.prototype.fmtaq.domain.task.Task;

import javax.persistence.Entity;
import java.util.UUID;

@Setter
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class ParallelTask extends Task {

    private Integer commandCounter;

    public ParallelTask(UUID id, TaskStatus taskStatus, Integer commandCounter, CommandSender commandSender) {
        super(id, taskStatus, commandSender);
        this.commandCounter = commandCounter;
    }

    @Override
    public void start() {
        getCommandSet().forEach(command -> getCommandSender().send(command));
    }

    void reduceCommandCounter() {
        commandCounter--;
    }
}
